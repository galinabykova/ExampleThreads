package ru.nsu.bykova;

import java.nio.CharBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/** Параллельное решение. */
public class ThreadPalindromeDetector implements PalindromeDetector {
  private final int threadNumber;

  public ThreadPalindromeDetector(int threadNumber) {
    this.threadNumber = threadNumber;
  }

  @Override
  public boolean isPalindrome(String string) {
    if (string.isEmpty()) {
      return true;
    }
    final int halfLen = string.length() / 2;
    // можно ли здесь делать общий taskDelimiter для всех потоков?
    final var taskDelimiter = new TaskDelimiter(halfLen, threadNumber);
    AtomicBoolean result = new AtomicBoolean(true);
    Thread[] threads = new Thread[threadNumber];
    for (int threadIndex = 0; threadIndex < threadNumber; ++threadIndex) {
      final int currentIndex = threadIndex;
      threads[currentIndex] =
          new Thread(
              () -> {
                // чтобы поэкспериментировать, как будет работать программа
                // при выбросе исключения потоком
                // можете в строке ниже заменить currentIndex на -1
                int myLen = taskDelimiter.lenThreadPart(currentIndex);
                int myOffset = taskDelimiter.offsetThreadPart(currentIndex);
                boolean threadResult =
                    PalindromeDetectorUtils.isPartPalindrome(
                        CharBuffer.wrap(string, myOffset, myOffset + myLen),
                        CharBuffer.wrap(
                            string,
                            string.length() - myOffset - myLen,
                            string.length() - myOffset));
                result.compareAndExchange(true, threadResult);
                // а что, если в самом начале выяснилось, что не палиндром?
                // можно время от времени проверять, что result всё ещё истина
              });
      threads[currentIndex].setUncaughtExceptionHandler(
          (thread, exception) -> {
            // https://stackoverflow.com/questions/9459657/is-multi-thread-output-from-system-out-println-interleaved
            synchronized (System.err) {
              // тут можно было бы залогировать, что что-то пошло не так
              System.err.println("Thread" + currentIndex + ":");
              exception.printStackTrace(System.err);
              // попробуйте здесь бросить исключение
              // https://stackoverflow-com.translate.goog/questions/6546193/how-to-catch-an-exception-from-a-thread?_x_tr_sl=en&_x_tr_tl=ru&_x_tr_hl=ru&_x_tr_pto=sc
            }
          });
      threads[currentIndex].start();
    }
    try {
      // тут главный поток ждёт, но можно его тоже загрузить было
      for (int threadIndex = 0; threadIndex < threadNumber; ++threadIndex) {
        threads[threadIndex].join();
      }
    } catch (InterruptedException e) {
      // мы ждём в главном потоке, который никто не должен интеррапнуть
      // поэтому здесь я бросаю RuntimeException - если мы сюда пришли, что пошло не так
      throw new RuntimeException("невозможная ситуация");
    }
    return result.get();
  }
}

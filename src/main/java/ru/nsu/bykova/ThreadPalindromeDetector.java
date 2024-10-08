package ru.nsu.bykova;

import java.nio.CharBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

/** Параллельное решение. */
public class ThreadPalindromeDetector implements PalindromeDetector {
    private final int threadCount;

    public ThreadPalindromeDetector(int threadNumber) {
        this.threadCount = threadNumber;
    }

    @Override
    public boolean isPalindrome(String string) {
        if (string.isEmpty()) {
            return true;
        }
        final int halfLen = string.length() / 2;
        // можно ли здесь делать общий taskDelimiter для всех потоков?
        final var taskDelimiter = new TaskDelimiter(halfLen, threadCount);
        AtomicBoolean result = new AtomicBoolean(true);
        final Thread[] threads = new Thread[threadCount];
        for (int threadIndex = 0; threadIndex < threadCount; ++threadIndex) {
            final int currentIndex = threadIndex;
            // создаём новый поток. Его статус будет - NEW. Пока он не запущен
            threads[currentIndex] = createThread(currentIndex, string, taskDelimiter, result);
            // здесь меняется его статус
            // после начала работы этого метода
            // поток начнёт выполняться
            threads[currentIndex].start();
        }
        try {
            // ожидаем, пока потоки выполнят свою работу
            // тут главный поток ждёт, но можно его тоже загрузить было
            for (int threadIndex = 0; threadIndex < threadCount; ++threadIndex) {
                threads[threadIndex].join();
            }
        } catch (InterruptedException e) {
            // мы ждём в главном потоке, который никто не должен интеррапнуть
            // поэтому здесь я бросаю RuntimeException - если мы сюда пришли, что пошло не так
            throw new RuntimeException("невозможная ситуация");
        }
        return result.get();
    }

    private Thread createThread(
            int currentIndex, String string, TaskDelimiter taskDelimiter, AtomicBoolean result) {
        var currentThread =
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
        // исключения остаются в потоке, где были брошены
        // можем указать, что делать, если в процессе выполнения
        // в потоке было брошено неперехваченное исключение
        // хендлер будет исполнен в том же потоке, где было брошено исключение
        currentThread.setUncaughtExceptionHandler(
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
        return currentThread;
    }
}

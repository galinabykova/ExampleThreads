package ru.nsu.bykova;

import java.nio.CharBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPalindromeDetector implements IPalindromeDetector {
    int threadNumber;

    public ThreadPalindromeDetector(int thread_number) {
        this.threadNumber = thread_number;
    }

    @Override
    public boolean IsPalindrome(char[] string) {
        final int halfLen = string.length / 2;
        // можно ли здесь делать общий taskDelimiter для всех потоков?
        final var taskDelimiter = new TaskDelimiter(halfLen, threadNumber);
        AtomicBoolean result = new AtomicBoolean(true);
        Thread[] threads = new Thread[threadNumber];
        for (int threadIndex = 0; threadIndex < threadNumber; ++threadIndex) {
            final int currentIndex = threadIndex;
            threads[currentIndex] = new Thread(() -> {
                int myLen = taskDelimiter.LenThreadPart(currentIndex);
                int myOffset = taskDelimiter.OffsetThreadPart(currentIndex);
                boolean threadResult = PalindromeDetectorUtils.IsPartPalindrome(
                        CharBuffer.wrap(string, myOffset, myLen),
                        CharBuffer.wrap(string, string.length - myOffset - 1, myLen)
                );
                result.compareAndExchange(true, threadResult);
                // а что, если в самом начале, кто-то выяснилось, что не палиндром?
                // можно время от времени проверять, что result всё ещё истина
            });
            threads[currentIndex].setUncaughtExceptionHandler(
                    (thread, exception) -> {
                        System.out.println("Случилось что-то странное" + exception.getMessage());
                    }
            );
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

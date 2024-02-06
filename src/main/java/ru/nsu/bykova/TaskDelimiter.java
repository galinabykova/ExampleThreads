package ru.nsu.bykova;

class TaskDelimiter {
    private final int taskNumber;
    private final int threadNumber;

    TaskDelimiter(int taskNumber, int threadNumber) {
        this.taskNumber = taskNumber;
        this.threadNumber = threadNumber;
    }

    int LenThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskNumber % threadNumber) {
            return taskNumber / threadNumber + 1;
        }
        return taskNumber / threadNumber;
    }

    int OffsetThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskNumber % threadNumber) {
            return taskNumber / threadNumber * threadIndex + threadIndex;
        }
        return taskNumber / threadNumber * threadIndex + taskNumber % threadNumber;
    }

    void checkArguments(int threadIndex) throws IllegalArgumentException {
        if (threadIndex >= threadNumber) {
            // что там с выбрасыванием исключений из потока?
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html#run-- не выбрасывает исключения
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setUncaughtExceptionHandler-java.lang.Thread.UncaughtExceptionHandler-
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setDefaultUncaughtExceptionHandler-java.lang.Thread.UncaughtExceptionHandler-
            // как это работает, можно посмотреть, например, http://www-public.imtbs-tsp.eu/~gibson/Teaching/CSC7322/L8-ExceptionsAndThreads.pdf на 32 слайде
            throw new IllegalArgumentException(String.format("threadIndex can be in [0, %d)", threadNumber));
        }
        if (threadIndex < 0) {
            throw new IllegalArgumentException("threadIndex < 0");
        }
    }
}

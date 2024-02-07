package ru.nsu.bykova;

/** Класс, разделяющий задачи между потоками. */
class TaskDelimiter {
    private final int taskNumber;
    private final int threadNumber;

    TaskDelimiter(int taskNumber, int threadNumber) {
        this.taskNumber = taskNumber;
        this.threadNumber = threadNumber;
    }

    /**
     * Длина кусочка, который считает поток с номером threadIndex.
     *
     * @param threadIndex от 0
     * @throws IllegalArgumentException если threadIndex <0 или >=threadNumber.
     */
    int lenThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskNumber % threadNumber) {
            return taskNumber / threadNumber + 1;
        }
        return taskNumber / threadNumber;
    }

    /**
     * Длина кусочка, который считает поток с номером threadIndex.
     *
     * @param threadIndex от 0
     * @throws IllegalArgumentException если threadIndex <0 или >=threadNumber.
     */
    int offsetThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskNumber % threadNumber) {
            return taskNumber / threadNumber * threadIndex + threadIndex;
        }
        return taskNumber / threadNumber * threadIndex + taskNumber % threadNumber;
    }

    void checkArguments(int threadIndex) throws IllegalArgumentException {
        if (threadIndex >= threadNumber) {
            // что там с выбрасыванием исключений из потока?
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html#run-- не
            // выбрасывает
            // исключения
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setUncaughtExceptionHandler-java.lang.Thread.UncaughtExceptionHandler-
            // https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#setDefaultUncaughtExceptionHandler-java.lang.Thread.UncaughtExceptionHandler-
            // как это работает, можно посмотреть, например,
            // http://www-public.imtbs-tsp.eu/~gibson/Teaching/CSC7322/L8-ExceptionsAndThreads.pdf
            // на 32
            // слайде
            var message = String.format("threadIndex can be in [0, %d)", threadNumber);
            throw new IllegalArgumentException(message);
        }
        if (threadIndex < 0) {
            throw new IllegalArgumentException("threadIndex < 0");
        }
    }
}

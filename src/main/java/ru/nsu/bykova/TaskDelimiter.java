package ru.nsu.bykova;

/** Класс, разделяющий задачи между потоками. */
class TaskDelimiter {
    // здесь мы изначально делим полезную работу между потоками,
    // потому что тут это легко сделать
    // создавать на каждую маленькую задачку поток плохо,
    // потому что создание потока имеет накладные расходы
    // но если задачи сложно поделить по потокам
    // можно, например, использовать ThreadPool
    private final int taskCount;
    private final int threadCount;

    TaskDelimiter(int taskCount, int threadCount) {
        this.taskCount = taskCount;
        this.threadCount = threadCount;
    }

    /**
     * Длина кусочка, который считает поток с номером threadIndex.
     *
     * @param threadIndex от 0
     * @throws IllegalArgumentException если threadIndex <0 или >=threadNumber.
     */
    int lenThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskCount % threadCount) {
            return taskCount / threadCount + 1;
        }
        return taskCount / threadCount;
    }

    /**
     * Длина кусочка, который считает поток с номером threadIndex.
     *
     * @param threadIndex от 0
     * @throws IllegalArgumentException если threadIndex <0 или >=threadNumber.
     */
    int offsetThreadPart(int threadIndex) throws IllegalArgumentException {
        checkArguments(threadIndex);
        if (threadIndex < taskCount % threadCount) {
            return taskCount / threadCount * threadIndex + threadIndex;
        }
        return taskCount / threadCount * threadIndex + taskCount % threadCount;
    }

    void checkArguments(int threadIndex) throws IllegalArgumentException {
        if (threadIndex >= threadCount) {
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
            var message = String.format("threadIndex can be in [0, %d)", threadCount);
            throw new IllegalArgumentException(message);
        }
        if (threadIndex < 0) {
            throw new IllegalArgumentException("threadIndex < 0");
        }
    }
}

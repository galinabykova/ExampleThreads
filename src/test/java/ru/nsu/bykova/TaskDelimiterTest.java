package ru.nsu.bykova;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/** Тесты для TaskDelimiter. */
public class TaskDelimiterTest {
    @ParameterizedTest(
            name =
                    "taskNumber = {0},zerothThreadsTaskNumber = {1},"
                            + "firstThreadTaskNumber = {2}, secondThreadTaskNumber = {3}")
    @CsvSource({
        "9 , 3, 3, 3",
        "3 , 1, 1, 1",
        "15, 5, 5, 5",
        "10, 4, 3, 3",
        "11, 4, 4, 3",
        "1 , 1, 0, 0"
    })
    void lenTest(
            int taskNumber,
            int zerothThreadsTaskNumber,
            int firstThreadTaskNumber,
            int secondThreadTaskNumber) {
        int threadNumber = 3;
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        Assertions.assertEquals(zerothThreadsTaskNumber, taskDelimiter.lenThreadPart(0));
        Assertions.assertEquals(firstThreadTaskNumber, taskDelimiter.lenThreadPart(1));
        Assertions.assertEquals(secondThreadTaskNumber, taskDelimiter.lenThreadPart(2));
    }

    @ParameterizedTest(
            name =
                    "taskNumber = {0},zerothThreadsTaskNumber = {1}, firstThreadTaskNumber = {2}, "
                            + "secondThreadTaskNumber = {3}")
    @CsvSource({
        "9 , 0, 3, 6",
        "3 , 0, 1, 2",
        "15, 0, 5, 10",
        "10, 0, 4, 7",
        "11, 0, 4, 8",
        "1 , 0, 1, 1"
    })
    void offsetTest(
            int taskNumber,
            int zerothThreadsTaskNumber,
            int firstThreadTaskNumber,
            int secondThreadTaskNumber) {
        int threadNumber = 3;
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        Assertions.assertEquals(zerothThreadsTaskNumber, taskDelimiter.offsetThreadPart(0));
        Assertions.assertEquals(firstThreadTaskNumber, taskDelimiter.offsetThreadPart(1));
        Assertions.assertEquals(secondThreadTaskNumber, taskDelimiter.offsetThreadPart(2));
    }

    @ParameterizedTest(name = "taskNumber = {0},threadNumber = {1}, threadIndex = {2}")
    @CsvSource({"1, 1, 1", "1, 1, 3", "1, 1, -5"})
    void invalidArgument(int taskNumber, int threadNumber, int threadIndex) {
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> taskDelimiter.lenThreadPart(threadIndex));
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> taskDelimiter.offsetThreadPart(threadIndex));
    }
}

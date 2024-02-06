package ru.nsu.bykova;

import jdk.jfr.Name;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskDelimiterTest {
    @ParameterizedTest(name =
            "taskNumber = {0},zerothThreadsTaskNumber = {1}, firstThreadTaskNumber = {2}, secondThreadTaskNumber = {3}")
    @CsvSource({
            "9 , 3, 3, 3",
            "3 , 1, 1, 1",
            "15, 5, 5, 5",
            "10, 4, 3, 3",
            "11, 4, 4, 3",
            "1 , 1, 0, 0"
    })
    void lenTest(int taskNumber,
                             int zerothThreadsTaskNumber, int firstThreadTaskNumber, int secondThreadTaskNumber) {
        int threadNumber = 3;
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        assertEquals(zerothThreadsTaskNumber, taskDelimiter.LenThreadPart(0));
        assertEquals(firstThreadTaskNumber, taskDelimiter.LenThreadPart(1));
        assertEquals(secondThreadTaskNumber, taskDelimiter.LenThreadPart(2));
    }

    @ParameterizedTest(name =
            "taskNumber = {0},zerothThreadsTaskNumber = {1}, firstThreadTaskNumber = {2}, secondThreadTaskNumber = {3}")
    @CsvSource({
            "9 , 0, 3, 6",
            "3 , 0, 1, 2",
            "15, 0, 5, 10",
            "10, 0, 4, 7",
            "11, 0, 4, 8",
            "1 , 0, 1, 1"
    })
    void offsetTest(int taskNumber,
                 int zerothThreadsTaskNumber, int firstThreadTaskNumber, int secondThreadTaskNumber) {
        int threadNumber = 3;
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        assertEquals(zerothThreadsTaskNumber, taskDelimiter.OffsetThreadPart(0));
        assertEquals(firstThreadTaskNumber, taskDelimiter.OffsetThreadPart(1));
        assertEquals(secondThreadTaskNumber, taskDelimiter.OffsetThreadPart(2));
    }

    @ParameterizedTest(name = "taskNumber = {0},threadNumber = {1}, threadIndex = {2}")
    @CsvSource({
            "1, 1, 1",
            "1, 1, 3",
            "1, 1, -5"
    })
    void invalidArgument(int taskNumber, int threadNumber, int threadIndex) {
        var taskDelimiter = new TaskDelimiter(taskNumber, threadNumber);
        assertThrows(IllegalArgumentException.class, () -> taskDelimiter.LenThreadPart(threadIndex));
        assertThrows(IllegalArgumentException.class, () -> taskDelimiter.OffsetThreadPart(threadIndex));
    }
}

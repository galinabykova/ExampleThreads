package ru.nsu.bykova;

import java.nio.CharBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Тесты для PalindromeDetectorUtils. */
class PalindromeDetectorUtilsTest {
    @Test
    void palindromeTest() {
        char[] palindrome = "abba".toCharArray();
        Assertions.assertTrue(
                PalindromeDetectorUtils.isPartPalindrome(
                        CharBuffer.wrap(palindrome, 0, 2), CharBuffer.wrap(palindrome, 2, 2)));
    }

    @Test
    void notPalindromeTest() {
        char[] palindrome = "abab".toCharArray();
        Assertions.assertFalse(
                PalindromeDetectorUtils.isPartPalindrome(
                        CharBuffer.wrap(palindrome, 0, 2), CharBuffer.wrap(palindrome, 2, 2)));
    }

    @Test
    void emptyTest() {
        char[] palindrome = "".toCharArray();
        Assertions.assertTrue(
                PalindromeDetectorUtils.isPartPalindrome(
                        CharBuffer.wrap(palindrome, 0, 0), CharBuffer.wrap(palindrome, 0, 0)));
    }

    @Test
    void differentLengthTest() {
        Assertions.assertFalse(
                PalindromeDetectorUtils.isPartPalindrome(
                        CharBuffer.wrap("lya", 0, 3), CharBuffer.wrap("myaNya", 0, 6)));
    }
}

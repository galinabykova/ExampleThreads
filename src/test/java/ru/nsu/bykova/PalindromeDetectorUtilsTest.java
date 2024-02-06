package ru.nsu.bykova;

import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PalindromeDetectorUtilsTest {
    @Test
    void palindromeTest() {
        char[] palindrome = "abba".toCharArray();
        assertTrue(PalindromeDetectorUtils.isPartPalindrome(
                CharBuffer.wrap(palindrome, 0, 2),
                CharBuffer.wrap(palindrome, 2, 2)
        ));
    }

    @Test
    void notPalindromeTest() {
        char[] palindrome = "abab".toCharArray();
        assertFalse(PalindromeDetectorUtils.isPartPalindrome(
                CharBuffer.wrap(palindrome, 0, 2),
                CharBuffer.wrap(palindrome, 2, 2)
        ));
    }

    @Test
    void emptyTest() {
        char[] palindrome = "".toCharArray();
        assertTrue(PalindromeDetectorUtils.isPartPalindrome(
                CharBuffer.wrap(palindrome, 0, 0),
                CharBuffer.wrap(palindrome, 0, 0)
        ));
    }

    @Test
    void differentLengthTest() {
        assertFalse(PalindromeDetectorUtils.isPartPalindrome(
                CharBuffer.wrap("lya", 0, 3),
                CharBuffer.wrap("myaNya", 0, 6)
        ));
    }
}
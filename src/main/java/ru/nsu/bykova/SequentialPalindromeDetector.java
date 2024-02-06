package ru.nsu.bykova;

import java.nio.CharBuffer;

/**
 * Последовательное решение.
 */
public class SequentialPalindromeDetector implements PalindromeDetector {
    @Override
    public boolean isPalindrome(char[] string) {
        int stringLengthHalf = string.length / 2;
        if (string.length % 2 == 0) {
            return PalindromeDetectorUtils.isPartPalindrome(
                    CharBuffer.wrap(string, 0, stringLengthHalf),
                    CharBuffer.wrap(string, stringLengthHalf, stringLengthHalf)
            );
        }
        return PalindromeDetectorUtils.isPartPalindrome(
                CharBuffer.wrap(string, 0, stringLengthHalf + 1),
                CharBuffer.wrap(string, stringLengthHalf, stringLengthHalf + 1));
    }
}

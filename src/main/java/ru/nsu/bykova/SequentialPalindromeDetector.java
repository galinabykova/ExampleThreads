package ru.nsu.bykova;

import java.nio.CharBuffer;

public class SequentialPalindromeDetector implements IPalindromeDetector {
    @Override
    public boolean IsPalindrome(char[] string) {
        int stringLengthHalf = string.length / 2;
        if (string.length % 2 == 0) {
            return PalindromeDetectorUtils.IsPartPalindrome(
                    CharBuffer.wrap(string, 0, stringLengthHalf),
                    CharBuffer.wrap(string, stringLengthHalf, stringLengthHalf)
            );
        }
        return PalindromeDetectorUtils.IsPartPalindrome(
                CharBuffer.wrap(string, 0, stringLengthHalf + 1),
                CharBuffer.wrap(string, stringLengthHalf, stringLengthHalf + 1));
    }
}

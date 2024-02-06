package ru.nsu.bykova;

class PalindromeDetectorUtils {
    static boolean IsPartPalindrome(CharSequence leftStringPart, CharSequence rightStringPart) {
        if (leftStringPart.length() != rightStringPart.length()) {
            return false;
        }
        int stringPartLength = leftStringPart.length();
        for (int currentPositionInLeftPart = 0;
             currentPositionInLeftPart < stringPartLength;
             ++currentPositionInLeftPart) {
            if (leftStringPart.charAt(currentPositionInLeftPart) !=
            rightStringPart.charAt(stringPartLength - currentPositionInLeftPart - 1))
                return false;
        }
        return true;
    }
}
package ru.nsu.bykova;

/** Полезные функции, которые нужны и в последовательном, и в параллельном решении. */
class PalindromeDetectorUtils {
  static boolean isPartPalindrome(CharSequence leftStringPart, CharSequence rightStringPart) {
    if (leftStringPart.length() != rightStringPart.length()) {
      return false;
    }
    int stringPartLength = leftStringPart.length();
    for (int currentPositionInLeftPart = 0;
        currentPositionInLeftPart < stringPartLength;
        ++currentPositionInLeftPart) {
      if (leftStringPart.charAt(currentPositionInLeftPart)
          != rightStringPart.charAt(stringPartLength - currentPositionInLeftPart - 1)) {
        return false;
      }
    }
    return true;
  }
}

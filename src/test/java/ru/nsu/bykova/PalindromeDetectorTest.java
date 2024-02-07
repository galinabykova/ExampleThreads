package ru.nsu.bykova;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

/** Тесты для PalindromeDetector. */
public class PalindromeDetectorTest {
  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void palindromeEvenLenTest(PalindromeDetector palindromeDetector) {
    Assertions.assertTrue(palindromeDetector.isPalindrome("abba"));
  }

  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void palindromeNotEvenLenTest(PalindromeDetector palindromeDetector) {
    Assertions.assertTrue(palindromeDetector.isPalindrome("ababa"));
  }

  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void notPalindromeEvenLenTest(PalindromeDetector palindromeDetector) {
    Assertions.assertFalse(palindromeDetector.isPalindrome("abab"));
  }

  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void notPalindromeNotEvenLenTest(PalindromeDetector palindromeDetector) {
    Assertions.assertFalse(palindromeDetector.isPalindrome("ababb"));
  }

  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void emptyTest(PalindromeDetector palindromeDetector) {
    Assertions.assertTrue(palindromeDetector.isPalindrome(""));
  }

  @ParameterizedTest()
  @ArgumentsSource(PalindromeDetectorProvider.class)
  void russianLettersTest(PalindromeDetector palindromeDetector) {
    Assertions.assertTrue(palindromeDetector.isPalindrome("аба"));
    Assertions.assertTrue(palindromeDetector.isPalindrome("абаба"));
    Assertions.assertTrue(palindromeDetector.isPalindrome("ffццццff"));
    Assertions.assertFalse(palindromeDetector.isPalindrome("абаб"));
    Assertions.assertFalse(palindromeDetector.isPalindrome("абаа"));
    Assertions.assertFalse(palindromeDetector.isPalindrome("абабабаа"));
    Assertions.assertFalse(palindromeDetector.isPalindrome("ацаffff"));
  }

  static class PalindromeDetectorProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
          Arguments.of(new SequentialPalindromeDetector()),
          Arguments.of(new ThreadPalindromeDetector(1)),
          Arguments.of(new ThreadPalindromeDetector(2)));
    }
  }
}

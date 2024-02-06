package ru.nsu.bykova;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

public class PalindromeDetectorTest {
    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void palindromeEvenLenTest(PalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.isPalindrome("abba".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void palindromeNotEvenLenTest(PalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.isPalindrome("ababa".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void notPalindromeEvenLenTest(PalindromeDetector palindromeDetector){
        assertFalse(palindromeDetector.isPalindrome("abab".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void notPalindromeNotEvenLenTest(PalindromeDetector palindromeDetector){
        assertFalse(palindromeDetector.isPalindrome("ababb".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void emptyTest(PalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.isPalindrome("".toCharArray()));
    }

    static class PalindromeDetectorProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new SequentialPalindromeDetector()),
                    Arguments.of(new ThreadPalindromeDetector(2))
            );
        }
    }
}

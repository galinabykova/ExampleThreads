package ru.nsu.bykova;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PalindromeDetectorTest {
    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void palindromeEvenLenTest(IPalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.IsPalindrome("abba".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void palindromeNotEvenLenTest(IPalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.IsPalindrome("ababa".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void notPalindromeEvenLenTest(IPalindromeDetector palindromeDetector){
        assertFalse(palindromeDetector.IsPalindrome("abab".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void notPalindromeNotEvenLenTest(IPalindromeDetector palindromeDetector){
        assertFalse(palindromeDetector.IsPalindrome("ababb".toCharArray()));
    }

    @ParameterizedTest()
    @ArgumentsSource(PalindromeDetectorProvider.class)
    void emptyTest(IPalindromeDetector palindromeDetector){
        assertTrue(palindromeDetector.IsPalindrome("".toCharArray()));
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

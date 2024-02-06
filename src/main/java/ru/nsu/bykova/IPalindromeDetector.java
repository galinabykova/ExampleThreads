package ru.nsu.bykova;

/**
 * Интерфейс, который должны наследовать
 * оба решения - последовательное и параллельное
 */
public interface IPalindromeDetector {
    public boolean isPalindrome(char[] string);
}

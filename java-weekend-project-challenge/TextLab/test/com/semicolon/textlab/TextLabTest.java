
package com.semicolon.textlab;

import com.semicolon.textlab.service.TextAnalyzer;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TextLabTest {

    @Test
    void testWhitespaceCleanup() {
        String text = "  Hello   \t  World!  \n  This is  Java.  ";

        TextAnalyzer analyzer = new TextAnalyzer(text);
        analyzer.cleanWhitespace();

        assertEquals("Hello World! This is Java.", analyzer.getEditedText());
    }

    @Test
    void testWholeWordSearchAndReplace() {
        String text = "Java is fun. JavaScript is different. I love JAVA.";

        TextAnalyzer analyzer = new TextAnalyzer(text);

        assertEquals(2, analyzer.countWordFrequency("Java"));

        analyzer.replaceWord("java", "Python");

        assertEquals(
                "Python is fun. JavaScript is different. I love Python.",
                analyzer.getEditedText()
        );
    }

    @Test
    void testPalindrome() {
        TextAnalyzer analyzer1 =
                new TextAnalyzer("A man, a plan, a canal: Panama!");

        assertTrue(analyzer1.isPalindrome());

        TextAnalyzer analyzer2 =
                new TextAnalyzer("Hello World");

        assertFalse(analyzer2.isPalindrome());
    }

    @Test
    void testEmptyInput() {
        TextAnalyzer analyzer = new TextAnalyzer("   \n\t   ");

        assertEquals(0, analyzer.getWordCount());
        assertEquals(0, analyzer.getSentenceCount());
        assertEquals(0, analyzer.getCharCountWithoutSpaces());

        assertFalse(analyzer.isPalindrome());
        assertTrue(analyzer.getLongestWords().isEmpty());

        assertDoesNotThrow(() -> analyzer.cleanWhitespace());
    }

    @Test
    void testPunctuationOnly() {
        TextAnalyzer analyzer = new TextAnalyzer("... !!! ???");

        assertEquals(0, analyzer.getWordCount());
        assertEquals(0, analyzer.getSentenceCount());
        assertTrue(analyzer.getLongestWords().isEmpty());
    }

    @Test
    void testLongestWordsAndSentenceCount() {
        String text = "The quick brown fox jumps over the lazy dog. The elephant runs!";

        TextAnalyzer analyzer = new TextAnalyzer(text);

        Set<String> longestWords = analyzer.getLongestWords();

        assertEquals(1, longestWords.size());
        assertTrue(longestWords.contains("elephant"));

        assertEquals(2, analyzer.getSentenceCount());
    }

    @Test
    void testSentenceCountWithoutClosingPunctuation() {
        TextAnalyzer analyzer = new TextAnalyzer(
                "First sentence. Second sentence without punctuation"
        );

        assertEquals(2, analyzer.getSentenceCount());
    }
}

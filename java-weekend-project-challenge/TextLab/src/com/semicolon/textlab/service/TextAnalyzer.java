
package com.semicolon.textlab.service;

import java.util.HashSet;
import java.util.Set;

public class TextAnalyzer {

    private final String originalText;
    private String editedText;

    public TextAnalyzer(String text) {
        originalText = text == null ? "" : text;
        editedText = originalText;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getEditedText() {
        return editedText;
    }

    public int getCharCountWithSpaces() {
        return editedText.length();
    }

    public int getCharCountWithoutSpaces() {
        return editedText.replaceAll("\\s", "").length();
    }

    public int getWordCount() {
        String[] words = editedText.trim().split("[^a-zA-Z0-9]+");

        if (editedText.trim().isEmpty()) return 0;

        return words.length;
    }

    public int getSentenceCount() {
        String text = editedText.trim();

        if (text.isEmpty() || text.matches("[^a-zA-Z0-9]+")) {
            return 0;
        }

        return text.split("[.!?]+\\s*|(?<=[.!?])\\s+").length;
    }

    public void cleanWhitespace() {
        editedText = editedText.trim().replaceAll("\\s+", " ");
    }

    public int countWordFrequency(String word) {
        if (word == null || word.trim().isEmpty()) return 0;

        String[] words = editedText.split("[^a-zA-Z0-9]+");
        int count = 0;

        for (String current : words) {
            if (current.equalsIgnoreCase(word.trim())) {
                count++;
            }
        }

        return count;
    }

    public void replaceWord(String oldWord, String newWord) {
        if (oldWord == null || oldWord.trim().isEmpty() || newWord == null) {
            return;
        }

        editedText = editedText.replaceAll(
                "(?i)\\b" + java.util.regex.Pattern.quote(oldWord.trim()) + "\\b",
                java.util.regex.Matcher.quoteReplacement(newWord)
        );
    }

    public boolean isPalindrome() {
        String text = editedText.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        if (text.isEmpty()) return false;

        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public Set<String> getLongestWords() {
        Set<String> longestWords = new HashSet<>();

        String[] words = editedText.split("[^a-zA-Z0-9]+");
        int maxLength = 0;

        for (String word : words) {
            if (word.length() > maxLength) {
                maxLength = word.length();
                longestWords.clear();
                longestWords.add(word);
            } else if (word.length() == maxLength && !word.isEmpty()) {
                longestWords.add(word);
            }
        }

        return longestWords;
    }
}

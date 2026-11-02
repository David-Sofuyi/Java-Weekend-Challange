package com.semicolon.textlab.ui;

import com.semicolon.textlab.service.TextAnalyzer;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Welcome to TextLab ---");
        System.out.println("Please paste or type your passage (press Enter to submit):");
        String input = scanner.nextLine();

        TextAnalyzer analyzer = new TextAnalyzer(input);
        boolean running = true;

        while (running) {
            System.out.println("\n--- TextLab Menu ---");
            System.out.println("1. Display Text Statistics");
            System.out.println("2. Clean Whitespace (collapse and trim)");
            System.out.println("3. Search for a Whole Word");
            System.out.println("4. Replace a Whole Word");
            System.out.println("5. Check Palindrome");
            System.out.println("6. Display Longest Words");
            System.out.println("7. Show Original vs Edited Text");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Chars (with spaces): " + analyzer.getCharCountWithSpaces());
                    System.out.println("Chars (without spaces): " + analyzer.getCharCountWithoutSpaces());
                    System.out.println("Words: " + analyzer.getWordCount());
                    System.out.println("Sentences: " + analyzer.getSentenceCount());
                    break;
                case "2":
                    analyzer.cleanWhitespace();
                    System.out.println("Whitespace cleaned! (Use option 7 to view)");
                    break;
                case "3":
                    System.out.print("Enter word to search: ");
                    String searchWord = scanner.nextLine();
                    int freq = analyzer.countWordFrequency(searchWord);
                    System.out.println("Frequency of \"" + searchWord + "\": " + freq);
                    break;
                case "4":
                    System.out.print("Enter word to replace: ");
                    String target = scanner.nextLine();
                    System.out.print("Enter replacement word: ");
                    String replacement = scanner.nextLine();
                    analyzer.replaceWord(target, replacement);
                    System.out.println("Word replaced! (Use option 7 to view)");
                    break;
                case "5":
                    if (analyzer.isPalindrome()) {
                        System.out.println("Yes! It is a palindrome.");
                    } else {
                        System.out.println("No. It is not a palindrome.");
                    }
                    break;
                case "6":
                    Set<String> longest = analyzer.getLongestWords();
                    if (longest.isEmpty()) {
                        System.out.println("No words found.");
                    } else {
                        System.out.println("Longest words: " + longest);
                    }
                    break;
                case "7":
                    System.out.println("--- Original Text ---");
                    System.out.println(analyzer.getOriginalText());
                    System.out.println("--- Edited Text ---");
                    System.out.println(analyzer.getEditedText());
                    break;
                case "8":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Choose 1-8.");
            }
        }
        scanner.close();
    }
}

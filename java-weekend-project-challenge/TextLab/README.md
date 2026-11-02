# 02 TextLab | Text Analysis & Cleanup

## Focus: Strings
An interactive text processing tool that analyzes, cleans, and manipulates written submissions without using third-party libraries.

## Features
- Count characters (with/without whitespace), words, and sentences.
- Clean whitespace (trims and collapses repeated spaces/tabs).
- Whole-word search and replace (ignores case, prevents "Java" from matching "JavaScript").
- Palindrome check (ignores case, spaces, and punctuation).
- Finds the longest word(s) in the passage.
- **Stretch Goal:** Preserves the original passage for side-by-side comparison.

## Key Design Decisions
- **StringBuilder:** Used `StringBuilder` for incremental text building in the `cleanWhitespace()` and `replaceWord()` methods to strictly adhere to memory efficiency guidelines.
- **Regex Boundaries:** Used `\b` word boundaries to ensure accurate whole-word matching.
- **Separation of Concerns:** All text-processing logic is isolated in `TextAnalyzer.java`, keeping the `Main.java` console UI clean.

## How to Run
Compile and run `Main.java` inside `src/com/semicolon/textlab/ui`.
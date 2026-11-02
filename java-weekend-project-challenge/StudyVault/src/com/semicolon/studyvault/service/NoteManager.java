
package com.semicolon.studyvault.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class NoteManager {

    private final Path notesDir = Paths.get("notes");
    private final Path backupsDir = Paths.get("backups");

    public NoteManager() {
        try {
            Files.createDirectories(notesDir);
            Files.createDirectories(backupsDir);
        } catch (IOException e) {
            System.out.println("Error creating directories: " + e.getMessage());
        }
    }

    private void validateFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()
                || filename.contains("/") || filename.contains("\\")
                || filename.contains("..")) {
            throw new IllegalArgumentException("Invalid filename.");
        }
    }

    public void createNote(String filename, String content, boolean overwrite) throws IOException {
        validateFilename(filename);
        Path file = notesDir.resolve(filename + ".txt");

        if (Files.exists(file) && !overwrite) {
            throw new IOException("Note already exists.");
        }

        Files.writeString(file, content);
    }

    public void appendToNote(String filename, String content) throws IOException {
        validateFilename(filename);
        Path file = notesDir.resolve(filename + ".txt");

        if (!Files.exists(file)) {
            throw new FileNotFoundException("Note not found.");
        }

        Files.writeString(file, System.lineSeparator() + content,
                StandardOpenOption.APPEND);
    }

    public String readNote(String filename) throws IOException {
        validateFilename(filename);
        Path file = notesDir.resolve(filename + ".txt");

        if (!Files.exists(file)) {
            throw new FileNotFoundException("Note not found.");
        }

        return Files.readString(file);
    }

    public void searchNotes(String keyword) throws IOException {
        File[] files = notesDir.toFile().listFiles(
                (dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No notes found.");
            return;
        }

        boolean found = false;

        for (File file : files) {
            int lineNumber = 1;

            for (String line : Files.readAllLines(file.toPath())) {
                if (line.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(file.getName() + " | Line "
                            + lineNumber + ": " + line);
                    found = true;
                }
                lineNumber++;
            }
        }

        if (!found) {
            System.out.println("Keyword not found.");
        }
    }

    public void deleteNote(String filename) throws IOException {
        validateFilename(filename);

        Path file = notesDir.resolve(filename + ".txt");

        if (!Files.deleteIfExists(file)) {
            throw new FileNotFoundException("Note not found.");
        }
    }

    public void exportNote(String filename) throws IOException {
        validateFilename(filename);

        Path source = notesDir.resolve(filename + ".txt");
        Path destination = backupsDir.resolve(filename + ".txt");

        if (!Files.exists(source)) {
            throw new FileNotFoundException("Note not found.");
        }

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public void restoreNote(String filename, boolean overwrite) throws IOException {
        validateFilename(filename);

        Path source = backupsDir.resolve(filename + ".txt");
        Path destination = notesDir.resolve(filename + ".txt");

        if (!Files.exists(source)) {
            throw new FileNotFoundException("Backup not found.");
        }

        if (Files.exists(destination) && !overwrite) {
            throw new IOException("Note already exists.");
        }

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    public String[] listNotes() {
        File[] files = notesDir.toFile().listFiles(
                (dir, name) -> name.endsWith(".txt"));

        if (files == null) {
            return new String[0];
        }

        String[] names = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName().replace(".txt", "");
        }

        return names;
    }
}

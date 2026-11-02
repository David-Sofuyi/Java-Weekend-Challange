package com.semicolon.studyvault.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NoteManager {
    private final Path notesDir;
    private final Path backupsDir;

    public NoteManager() {
        this.notesDir = Paths.get("notes");
        this.backupsDir = Paths.get("backups");
        createDirectories();
    }

    private void createDirectories() {
        try {
            if (!Files.exists(notesDir)) Files.createDirectories(notesDir);
            if (!Files.exists(backupsDir)) Files.createDirectories(backupsDir);
        } catch (IOException exception) {
            System.err.println("Failed to create directories: " + exception.getMessage());
        }
    }

    private void validateFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be blank.");
        }
        if (filename.contains("/") || filename.contains("\\")) {
            throw new IllegalArgumentException("Filename cannot contain path separators.");
        }
        if (filename.contains("..")) {
            throw new IllegalArgumentException("Filename cannot contain '..'.");
        }
    }

    public void createNote(String filename, String content, boolean overwrite) throws IOException {
        validateFilename(filename);
        Path filePath = notesDir.resolve(filename + ".txt");

        if (Files.exists(filePath) && !overwrite) {
            throw new IOException("File already exists. Overwrite refused.");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }

    public void appendToNote(String filename, String content) throws IOException {
        validateFilename(filename);
        Path filePath = notesDir.resolve(filename + ".txt");

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Note not found.");
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.APPEND)) {
            writer.newLine();
            writer.write(content);
        }
    }

    public String readNote(String filename) throws IOException {
        validateFilename(filename);
        Path filePath = notesDir.resolve(filename + ".txt");

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Note not found.");
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    public void searchNotes(String keyword) throws IOException {
        File[] files = notesDir.toFile().listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No notes found.");
            return;
        }

        boolean found = false;
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                int lineNumber = 1;
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains(keyword.toLowerCase())) {
                        System.out.println("File: " + file.getName() + " | Line " + lineNumber + ": " + line);
                        found = true;
                    }
                    lineNumber++;
                }
            }
        }
        if (!found) {
            System.out.println("Keyword not found in any note.");
        }
    }

    public void deleteNote(String filename) throws IOException {
        validateFilename(filename);
        Path filePath = notesDir.resolve(filename + ".txt");
        if (!Files.deleteIfExists(filePath)) {
            throw new FileNotFoundException("Note not found.");
        }
    }

    public void exportNote(String filename) throws IOException {
        validateFilename(filename);
        Path source = notesDir.resolve(filename + ".txt");
        Path destination = backupsDir.resolve(filename + ".txt");

        if (!Files.exists(source)) {
            throw new FileNotFoundException("Source note not found.");
        }

        try (FileInputStream fis = new FileInputStream(source.toFile());
             FileOutputStream fos = new FileOutputStream(destination.toFile())) {

            byte[] buffer = new byte[4096]; // 4KB buffer
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    public void restoreNote(String filename, boolean overwrite) throws IOException {
        validateFilename(filename);
        Path source = backupsDir.resolve(filename + ".txt");
        Path destination = notesDir.resolve(filename + ".txt");

        if (!Files.exists(source)) {
            throw new FileNotFoundException("Backup not found.");
        }
        if (Files.exists(destination) && !overwrite) {
            throw new IOException("Note already exists in the main directory. Overwrite refused.");
        }

        try (FileInputStream fis = new FileInputStream(source.toFile());
             FileOutputStream fos = new FileOutputStream(destination.toFile())) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    public String[] listNotes() {
        File[] files = notesDir.toFile().listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return new String[0];

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            names[i] = name.substring(0, name.length() - 4); // remove ".txt"
        }
        return names;
    }
}

package com.semicolon.studyvault;

import com.semicolon.studyvault.service.NoteManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class StudyVaultTest {

    private NoteManager manager;
    private final String testFileName = "test_note";

    @BeforeEach
    void setUp() {
        manager = new NoteManager();
        try {
            Files.deleteIfExists(Paths.get("notes", testFileName + ".txt"));
            Files.deleteIfExists(Paths.get("backups", testFileName + ".txt"));
        } catch (IOException ignored) {}
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(Paths.get("notes", testFileName + ".txt"));
            Files.deleteIfExists(Paths.get("backups", testFileName + ".txt"));
        } catch (IOException ignored) {}
    }

    @Test
    void testInvalidFilenames() {
        assertThrows(IllegalArgumentException.class, () -> {
            manager.createNote("   ", "Content", false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            manager.createNote("../hacked", "Content", false);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            manager.createNote("folder/note", "Content", false);
        });
    }

    @Test
    void testSafeAppending() throws Exception {
        manager.createNote(testFileName, "First line.", false);
        manager.appendToNote(testFileName, "Second line.");

        String content = manager.readNote(testFileName);

        assertTrue(content.contains("First line."));
        assertTrue(content.contains("Second line."));
    }

    @Test
    void testUTF8Encoding() throws Exception {
        String utf8Content = "Java is fun! ☕ 漢字 émojis 🚀";
        manager.createNote(testFileName, utf8Content, false);

        Path filePath = Paths.get("notes", testFileName + ".txt");
        String rawContent = Files.readString(filePath, StandardCharsets.UTF_8);

        assertEquals(utf8Content, rawContent);
    }

    @Test
    void testExportAndRestore() throws Exception {
        String originalContent = "This is a note to be exported.";
        manager.createNote(testFileName, originalContent, false);

        manager.exportNote(testFileName);

        File backupFile = new File("backups/" + testFileName + ".txt");
        assertTrue(backupFile.exists());

        String backupContent = Files.readString(backupFile.toPath(), StandardCharsets.UTF_8);
        assertEquals(originalContent, backupContent);

        manager.deleteNote(testFileName);
        assertFalse(new File("notes/" + testFileName + ".txt").exists());

        manager.restoreNote(testFileName, false);
        assertTrue(new File("notes/" + testFileName + ".txt").exists());
        assertEquals(originalContent, manager.readNote(testFileName));
    }

    @Test
    void testMissingFilesAndOverwrites() throws Exception {
        assertThrows(IOException.class, () -> manager.readNote("non_existent_file"));

        manager.createNote(testFileName, "Original", false);

        assertThrows(IOException.class, () -> manager.createNote(testFileName, "New", false));

        manager.exportNote(testFileName);
        assertThrows(IOException.class, () -> manager.restoreNote(testFileName, false));
    }
}

# 03 StudyVault | File-Based Notes

## Focus: File Handling and I/O Streams
A persistent notes manager that saves study notes directly to the hard drive, allowing users to create, append, search, and back up their data.

## Features
- Create multi-line notes and save them as `.txt` files.
- List, open, and append to existing notes.
- Search note contents and display matching line numbers.
- Delete notes with confirmation.
- Export notes to a `backups/` directory.
- Restore backups without silently overwriting existing notes.
- **Stretch Goal:** Reports file size, line count, and last modified time (in progress).

## Key Design Decisions
- **I/O Streams:** Used `BufferedReader` and `BufferedWriter` with UTF-8 encoding for safe text operations.
- **Byte Copying:** Implemented file backups using `FileInputStream` and `FileOutputStream` with a manual `byte[]` buffer (no `Files.copy()`), satisfying the requirement for low-level I/O stream mastery.
- **Security:** Filenames are strictly validated to prevent directory traversal (e.g., `../`) and path separators.
- **Resource Management:** All file streams are managed using `try-with-resources` to guarantee they close even if an exception occurs.

## How to Run
Compile and run `Main.java` inside `src/com/semicolon/studyvault/ui`.
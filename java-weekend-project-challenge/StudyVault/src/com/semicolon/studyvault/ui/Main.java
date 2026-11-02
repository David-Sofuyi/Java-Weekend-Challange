package com.semicolon.studyvault.ui;

import com.semicolon.studyvault.service.NoteManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NoteManager manager = new NoteManager();
        boolean running = true;

        System.out.println("Welcome to StudyVault!");

        while (running) {
            System.out.println("\n--- StudyVault Menu ---");
            System.out.println("1. Create Note");
            System.out.println("2. List Notes");
            System.out.println("3. Open Note");
            System.out.println("4. Append to Note");
            System.out.println("5. Search Notes");
            System.out.println("6. Delete Note");
            System.out.println("7. Export Note");
            System.out.println("8. Restore Exported Note");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Enter filename (no extensions): ");
                        String newFile = scanner.nextLine();
                        System.out.println("Enter content (type 'END' on a new line to finish):");
                        String content = readMultiline(scanner);
                        manager.createNote(newFile, content, false);
                        System.out.println("Note created!");
                        break;

                    case "2":
                        String[] notes = manager.listNotes();
                        if (notes.length == 0) {
                            System.out.println("No notes saved.");
                        } else {
                            for (String note : notes) {
                                System.out.println("- " + note);
                            }
                        }
                        break;

                    case "3":
                        System.out.print("Enter filename to open: ");
                        String openFile = scanner.nextLine();
                        System.out.println("\n--- Content of " + openFile + " ---");
                        System.out.println(manager.readNote(openFile));
                        break;

                    case "4":
                        System.out.print("Enter filename to append: ");
                        String appendFile = scanner.nextLine();
                        System.out.println("Enter content to append (type 'END' on a new line to finish):");
                        String appendContent = readMultiline(scanner);
                        manager.appendToNote(appendFile, appendContent);
                        System.out.println("Content appended!");
                        break;

                    case "5":
                        System.out.print("Enter keyword to search: ");
                        String keyword = scanner.nextLine();
                        manager.searchNotes(keyword);
                        break;

                    case "6":
                        System.out.print("Enter filename to delete: ");
                        String delFile = scanner.nextLine();
                        System.out.print("Are you sure? (yes/no): ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("yes")) {
                            manager.deleteNote(delFile);
                            System.out.println("Note deleted.");
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                        break;

                    case "7":
                        System.out.print("Enter filename to export: ");
                        String exportFile = scanner.nextLine();
                        manager.exportNote(exportFile);
                        System.out.println("Note exported to backups.");
                        break;

                    case "8":
                        System.out.print("Enter filename to restore: ");
                        String restoreFile = scanner.nextLine();
                        try {
                            manager.restoreNote(restoreFile, false);
                            System.out.println("Note restored.");
                        } catch (IOException exception) {
                            if (exception.getMessage().contains("Overwrite")) {
                                System.out.print("Note already exists. Overwrite? (yes/no): ");
                                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                    manager.restoreNote(restoreFile, true);
                                    System.out.println("Note overwritten and restored.");
                                } else {
                                    System.out.println("Restore cancelled.");
                                }
                            } else {
                                throw exception;
                            }
                        }
                        break;

                    case "9":
                        System.out.println("Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException | IOException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }
        scanner.close();
    }

    private static String readMultiline(Scanner scanner) {
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("END")) break;
            builder.append(line).append("\n");
        }
        return builder.toString();
    }
}

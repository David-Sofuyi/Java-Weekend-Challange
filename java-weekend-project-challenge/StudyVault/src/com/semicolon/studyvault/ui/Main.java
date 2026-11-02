
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
            System.out.println("8. Restore Note");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {

                    case "1":
                        System.out.print("Enter filename: ");
                        String name = scanner.nextLine();

                        System.out.println("Enter content (type END to finish):");
                        String content = readMultiline(scanner);

                        manager.createNote(name, content, false);
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
                        System.out.print("Enter filename: ");
                        System.out.println(manager.readNote(scanner.nextLine()));
                        break;

                    case "4":
                        System.out.print("Enter filename: ");
                        String file = scanner.nextLine();

                        System.out.println("Enter content (type END to finish):");
                        manager.appendToNote(file, readMultiline(scanner));

                        System.out.println("Content appended!");
                        break;

                    case "5":
                        System.out.print("Enter keyword: ");
                        manager.searchNotes(scanner.nextLine());
                        break;

                    case "6":
                        System.out.print("Enter filename: ");
                        name = scanner.nextLine();

                        System.out.print("Are you sure? (yes/no): ");

                        if (scanner.nextLine().equalsIgnoreCase("yes")) {
                            manager.deleteNote(name);
                            System.out.println("Note deleted!");
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                        break;

                    case "7":
                        System.out.print("Enter filename: ");
                        manager.exportNote(scanner.nextLine());
                        System.out.println("Note exported!");
                        break;

                    case "8":
                        System.out.print("Enter filename: ");
                        name = scanner.nextLine();

                        try {
                            manager.restoreNote(name, false);
                            System.out.println("Note restored!");
                        } catch (IOException e) {
                            if (e.getMessage().contains("already exists")) {
                                System.out.print("Overwrite existing note? (yes/no): ");

                                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                                    manager.restoreNote(name, true);
                                    System.out.println("Note restored!");
                                } else {
                                    System.out.println("Restore cancelled.");
                                }
                            } else {
                                throw e;
                            }
                        }
                        break;

                    case "9":
                        System.out.println("Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static String readMultiline(Scanner scanner) {
        StringBuilder content = new StringBuilder();

        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            content.append(line).append("\n");
        }

        return content.toString();
    }
}

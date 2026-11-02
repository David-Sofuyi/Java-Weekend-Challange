
package com.semicolon.coorthub;

import com.semicolon.coorthub.model.Learner;
import com.semicolon.coorthub.service.CohortManager;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CohortManager manager = new CohortManager();

        System.out.println("Welcome to CohortHub!");

        while (true) {

            System.out.println("\n--- CohortHub Menu ---");
            System.out.println("1. Register Learner");
            System.out.println("2. View Learners");
            System.out.println("3. Create Study Group");
            System.out.println("4. Add Learner to Group");
            System.out.println("5. Add Learner to Support Queue");
            System.out.println("6. Attend Next Support Request");
            System.out.println("7. Display Stats");
            System.out.println("8. Remove Learner");
            System.out.println("9. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        System.out.print("Enter ID: ");
                        String id = scanner.nextLine();

                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();

                        manager.registerLearner(id, name, email);
                        System.out.println("Learner registered!");
                        break;

                    case "2":
                        List<Learner> learners = manager.getSortedLearners();

                        if (learners.isEmpty()) {
                            System.out.println("No learners registered.");
                        } else {
                            for (Learner learner : learners) {
                                System.out.println(learner);
                            }
                        }
                        break;

                    case "3":
                        System.out.print("Enter group name: ");
                        String group = scanner.nextLine();

                        manager.createGroup(group);
                        System.out.println("Group created!");
                        break;

                    case "4":
                        System.out.print("Enter Learner ID: ");
                        String learnerId = scanner.nextLine();

                        System.out.print("Enter Group Name: ");
                        String groupName = scanner.nextLine();

                        manager.addLearnerToGroup(learnerId, groupName);
                        System.out.println("Learner added to group!");
                        break;

                    case "5":
                        System.out.print("Enter Learner ID: ");
                        String supportId = scanner.nextLine();

                        manager.addToSupportQueue(supportId);
                        System.out.println("Learner added to support queue.");
                        break;

                    case "6":
                        Learner next = manager.attendNextInQueue();

                        if (next == null) {
                            System.out.println("Support queue is empty.");
                        } else {
                            System.out.println("Now attending to: " + next.getName());
                        }
                        break;

                    case "7":
                        System.out.println("Total Learners: " + manager.getTotalLearners());
                        System.out.println("Total Groups: " + manager.getGroupCount());
                        System.out.println("Waiting for Support: " + manager.getWaitingCount());
                        break;

                    case "8":
                        System.out.print("Enter Learner ID to remove: ");
                        String removeId = scanner.nextLine();

                        manager.removeLearner(removeId);
                        System.out.println("Learner removed!");
                        break;

                    case "9":
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

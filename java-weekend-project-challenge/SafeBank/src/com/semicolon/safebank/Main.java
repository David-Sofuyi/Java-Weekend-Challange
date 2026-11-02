package com.semicolon.safebank;

import com.semicolon.safebank.exception.InsufficientFundsException;
import com.semicolon.safebank.exception.InvalidAmountException;
import com.semicolon.safebank.exception.WithdrawalLimitExceededException;
import com.semicolon.safebank.model.Account;
import com.semicolon.safebank.model.Transaction;
import com.semicolon.safebank.service.BankService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bank = null;

        System.out.println("Welcome to SafeBank!");

        while (bank == null) {
            try {
                System.out.print("Enter account holder name: ");
                String name = scanner.nextLine();
                System.out.print("Enter opening balance: ");
                BigDecimal openingBalance = new BigDecimal(scanner.nextLine());

                Account newAccount = new Account(name, openingBalance);
                bank = new BankService(newAccount);
                System.out.println("Account created successfully for " + newAccount.getAccountHolderName());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- SafeBank Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try {
                        System.out.print("Enter deposit amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());
                        bank.deposit(amount);
                        System.out.println("Deposit successful.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Non-numeric input. Please enter a number.");
                    } catch (InvalidAmountException e) {
                        System.out.println("Transaction Failed: " + e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Enter withdrawal amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());
                        bank.withdraw(amount);
                        System.out.println("Withdrawal successful.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Non-numeric input. Please enter a number.");
                    } catch (InvalidAmountException | InsufficientFundsException | WithdrawalLimitExceededException e) {
                        System.out.println("Transaction Failed: " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Current Balance: " + bank.getAccount().getBalance());
                    break;

                case "4":
                    System.out.println("--- Transaction History ---");
                    if (bank.getHistory().isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (Transaction t : bank.getHistory()) {
                            System.out.println(t.toString());
                        }
                    }
                    break;

                case "5":
                    System.out.println("Thank you for using SafeBank. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid menu selection. Please choose 1-5.");
            }
        }
        scanner.close();
    }
}

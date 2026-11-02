package com.semicolon.safebank.service;

import com.semicolon.safebank.exception.InsufficientFundsException;
import com.semicolon.safebank.exception.InvalidAmountException;
import com.semicolon.safebank.exception.WithdrawalLimitExceededException;
import com.semicolon.safebank.model.Account;
import com.semicolon.safebank.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankService {
    private final Account account;
    private final List<Transaction> history;

    public BankService(Account account) {
        this.account = account;
        this.history = new ArrayList<>();
        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            history.add(new Transaction("Opening Balance", account.getBalance()));
        }
    }

    public Account getAccount() {
        return account;
    }

    public List<Transaction> getHistory() {
        return new ArrayList<>(history);
    }

    public void deposit(BigDecimal amount) throws InvalidAmountException {
        account.deposit(amount);
        history.add(new Transaction("Deposit", amount));
    }

    public void withdraw(BigDecimal amount) throws InvalidAmountException, InsufficientFundsException, WithdrawalLimitExceededException {
        account.withdraw(amount);
        history.add(new Transaction("Withdrawal", amount));
    }

    public void transferTo(Account targetAccount, BigDecimal amount) throws InvalidAmountException, InsufficientFundsException, WithdrawalLimitExceededException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Transfer amount must be greater than zero.");
        }
        if (amount.compareTo(account.getBalance()) > 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }
        if (amount.compareTo(new BigDecimal("100000.00")) > 0) {
            throw new WithdrawalLimitExceededException("Transfer exceeds the 100,000 limit.");
        }

        account.withdraw(amount);
        targetAccount.deposit(amount);

        history.add(new Transaction("Transfer Out", amount));
    }
}

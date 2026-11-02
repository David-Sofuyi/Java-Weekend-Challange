package com.semicolon.safebank;

import com.semicolon.safebank.exception.InsufficientFundsException;
import com.semicolon.safebank.exception.InvalidAmountException;
import com.semicolon.safebank.exception.WithdrawalLimitExceededException;
import com.semicolon.safebank.model.Account;
import com.semicolon.safebank.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class SafeBankTest {

    private Account account;
    private BankService bankService;

    @BeforeEach
    void setUp() {
        account = new Account("John Doe", new BigDecimal("50000.00"));
        bankService = new BankService(account);
    }

    @Test
    void testAccountCreationRules() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Account("   ", new BigDecimal("100.00"));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Account("Jane Doe", new BigDecimal("-50.00"));
        });
    }

    @Test
    void testInvalidDeposit() {
        BigDecimal depositAmount = new BigDecimal("-500.00");

        assertThrows(InvalidAmountException.class, () -> {
            bankService.deposit(depositAmount);
        });

        assertEquals(new BigDecimal("50000.00"), account.getBalance());
    }

    @Test
    void testWithdrawalExceedingBalance() {
        BigDecimal withdrawalAmount = new BigDecimal("60000.00");

        assertThrows(InsufficientFundsException.class, () -> {
            bankService.withdraw(withdrawalAmount);
        });

        assertEquals(new BigDecimal("50000.00"), account.getBalance());
    }

    @Test
    void testWithdrawalLimitExceeded() throws Exception {
        bankService.deposit(new BigDecimal("200000.00"));

        BigDecimal withdrawalAmount = new BigDecimal("120000.00"); // Over the 100,000 limit

        assertThrows(WithdrawalLimitExceededException.class, () -> {
            bankService.withdraw(withdrawalAmount);
        });

        assertEquals(new BigDecimal("250000.00"), account.getBalance());
    }

    @Test
    void testSuccessImmediatelyAfterFailure() throws Exception {
        try {
            bankService.withdraw(new BigDecimal("999999.00"));
        } catch (InsufficientFundsException e) {

        }

        bankService.deposit(new BigDecimal("5000.00"));
        assertEquals(new BigDecimal("55000.00"), account.getBalance());

        assertEquals(2, bankService.getHistory().size());

        assertEquals("Opening Balance", bankService.getHistory().get(0).toString().split(":")[0].substring(11).trim());
        assertEquals("Deposit", bankService.getHistory().get(1).toString().split(":")[0].substring(11).trim());
    }
}

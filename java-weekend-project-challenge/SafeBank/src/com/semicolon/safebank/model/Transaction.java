package com.semicolon.safebank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private final String type;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    public Transaction(String type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", timestamp.toLocalTime(), type, amount);
    }
}

# 01 SafeBank | Transaction Simulator

## Focus: Exception Handling
A console-based banking simulator designed to protect account balances from invalid transactions using custom exceptions and strict business rules.

## Features
- Create an account with a holder name and opening balance.
- Deposit and withdraw funds.
- Single withdrawal limit of 100,000.
- View current balance and transaction history.
- **Stretch Goal:** Transfers between two accounts (validates the entire transfer before modifying balances).

## Key Design Decisions
- **`BigDecimal` for Money:** Used `BigDecimal` instead of `double` to prevent floating-point precision errors.
- **Custom Exceptions:** Created `InvalidAmountException`, `InsufficientFundsException`, and `WithdrawalLimitExceededException` to handle specific business rule violations.
- **State Protection:** If a transaction is rejected, the exception is thrown *before* any changes are made to the balance or history, ensuring a failed transaction leaves the account state unchanged.

## How to Run
Compile and run `Main.java` inside `src/com/semicolon/safebank`.
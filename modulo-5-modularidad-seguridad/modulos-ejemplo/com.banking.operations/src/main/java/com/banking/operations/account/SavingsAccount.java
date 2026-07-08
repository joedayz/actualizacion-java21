package com.banking.operations.account;

import com.banking.core.account.BankAccount;
import com.banking.core.exception.InsufficientFundsException;
import com.banking.core.exception.InvalidAccountOperationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementación de una cuenta de ahorros.
 * Esta clase NO es pública en el módulo (está en el paquete operations.account que no se exporta).
 */
public class SavingsAccount implements BankAccount {
    
    private final String accountNumber;
    private final String accountHolder;
    private BigDecimal balance;
    private final List<String> transactionHistory;
    private LocalDateTime lastTransactionTime;
    private static final BigDecimal MIN_BALANCE = BigDecimal.ZERO;
    private static final BigDecimal WITHDRAWAL_LIMIT = new BigDecimal("5000.00");
    
    public SavingsAccount(String accountNumber, String accountHolder, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAccountOperationException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new LinkedList<>();
        this.lastTransactionTime = LocalDateTime.now();
        
        if (initialBalance.compareTo(BigDecimal.ZERO) > 0) {
            addTransaction("Initial deposit: " + initialBalance);
        }
    }
    
    @Override
    public String getAccountNumber() {
        return accountNumber;
    }
    
    @Override
    public String getAccountHolder() {
        return accountHolder;
    }
    
    @Override
    public BigDecimal getBalance() {
        return balance;
    }
    
    @Override
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAccountOperationException("Deposit amount must be positive");
        }
        
        this.balance = this.balance.add(amount);
        addTransaction("Deposit: +" + amount);
    }
    
    @Override
    public void withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAccountOperationException("Withdrawal amount must be positive");
        }
        
        if (amount.compareTo(WITHDRAWAL_LIMIT) > 0) {
            throw new InvalidAccountOperationException(
                "Withdrawal limit is " + WITHDRAWAL_LIMIT + " for savings accounts"
            );
        }
        
        if (this.balance.subtract(amount).compareTo(MIN_BALANCE) < 0) {
            throw new InsufficientFundsException(
                "Insufficient funds. Balance: " + this.balance + ", Requested: " + amount
            );
        }
        
        this.balance = this.balance.subtract(amount);
        addTransaction("Withdrawal: -" + amount);
    }
    
    @Override
    public String getTransactionHistory() {
        return String.join("\n", transactionHistory);
    }
    
    @Override
    public LocalDateTime getLastTransactionTime() {
        return lastTransactionTime;
    }
    
    private void addTransaction(String description) {
        this.lastTransactionTime = LocalDateTime.now();
        this.transactionHistory.add(
            String.format("[%s] %s", lastTransactionTime, description)
        );
    }
    
    @Override
    public String toString() {
        return String.format(
            "SavingsAccount{number='%s', holder='%s', balance=%s}",
            accountNumber, accountHolder, balance
        );
    }
}


package com.banking.core.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Interfaz que define las operaciones básicas de una cuenta bancaria.
 * Esta es la interfaz pública del módulo core.
 */
public interface BankAccount {
    
    /**
     * Obtiene el número de cuenta
     */
    String getAccountNumber();
    
    /**
     * Obtiene el titular de la cuenta
     */
    String getAccountHolder();
    
    /**
     * Obtiene el balance actual
     */
    BigDecimal getBalance();
    
    /**
     * Realiza un depósito
     */
    void deposit(BigDecimal amount);
    
    /**
     * Realiza un retiro
     */
    void withdraw(BigDecimal amount);
    
    /**
     * Obtiene el histórico de transacciones
     */
    String getTransactionHistory();
    
    /**
     * Obtiene la última transacción
     */
    LocalDateTime getLastTransactionTime();
}


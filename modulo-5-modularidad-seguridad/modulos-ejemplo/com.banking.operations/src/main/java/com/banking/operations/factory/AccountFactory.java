package com.banking.operations.factory;

import com.banking.core.account.BankAccount;
import com.banking.operations.account.SavingsAccount;

import java.math.BigDecimal;

/**
 * Factory para crear instancias de cuentas bancarias.
 * Esta clase es interna del módulo operations.
 */
public class AccountFactory {
    
    private AccountFactory() {
        // No se puede instanciar
    }
    
    /**
     * Crea una nueva cuenta de ahorros
     */
    public static BankAccount createSavingsAccount(
            String accountNumber, 
            String accountHolder, 
            BigDecimal initialBalance) {
        return new SavingsAccount(accountNumber, accountHolder, initialBalance);
    }
}


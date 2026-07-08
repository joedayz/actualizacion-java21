package com.banking.core.exception;

/**
 * Excepción lanzada cuando una operación de cuenta resulta en fondos insuficientes.
 */
public class InsufficientFundsException extends RuntimeException {
    
    public InsufficientFundsException(String message) {
        super(message);
    }
    
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }
}


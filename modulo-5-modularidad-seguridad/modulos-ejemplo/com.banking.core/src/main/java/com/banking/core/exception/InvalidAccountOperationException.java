package com.banking.core.exception;

/**
 * Excepción lanzada cuando una operación de cuenta es inválida.
 */
public class InvalidAccountOperationException extends RuntimeException {
    
    public InvalidAccountOperationException(String message) {
        super(message);
    }
    
    public InvalidAccountOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}


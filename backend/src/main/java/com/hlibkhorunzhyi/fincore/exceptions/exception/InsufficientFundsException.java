package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(Long id) {
        super("Insufficient funds in account '%s'".formatted(id));
    }
}

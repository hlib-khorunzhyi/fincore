package com.hlibkhorunzhyi.fincore.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account with id '%s' not found".formatted(id));
    }
}

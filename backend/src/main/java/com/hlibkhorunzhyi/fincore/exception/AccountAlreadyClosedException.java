package com.hlibkhorunzhyi.fincore.exception;

public class AccountAlreadyClosedException extends RuntimeException {
    public AccountAlreadyClosedException(Long id) {
        super("Account with id '%s' has already closed".formatted(id));
    }
}

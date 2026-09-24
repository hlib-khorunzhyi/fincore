package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(Long id) {
        super("Account with id '%s' is not active".formatted(id));
    }
}

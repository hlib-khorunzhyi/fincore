package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class AccountHasBalanceException extends RuntimeException {
    public AccountHasBalanceException(Long id) {
        super("Cannot close account with id '%s' because its balance is not zero".formatted(id));
    }
}

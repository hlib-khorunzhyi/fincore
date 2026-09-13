package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User with id '%s' not found".formatted(id));
    }
    public UserNotFoundException(String email) {
        super("User with email '%s' not found".formatted(email));
    }
}

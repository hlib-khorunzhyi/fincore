package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class TransferNotFoundException extends RuntimeException {

    public TransferNotFoundException(Long id) {
        super("Transfer with id '%s' not found".formatted(id));
    }
}

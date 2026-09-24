package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException() {
        super("Source and destination account must be different");
    }
}

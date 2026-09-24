package com.hlibkhorunzhyi.fincore.exceptions.exception;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException() {
        super("Source and destination accounts must have the same currency.");
    }
}

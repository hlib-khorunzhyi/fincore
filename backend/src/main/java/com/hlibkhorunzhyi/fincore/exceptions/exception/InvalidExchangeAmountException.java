package com.hlibkhorunzhyi.fincore.exceptions.exception;

import com.hlibkhorunzhyi.fincore.account.entity.Currency;

public class InvalidExchangeAmountException extends RuntimeException {
    public InvalidExchangeAmountException(Currency currency) {
        super("The converted amount is below the minimum unit of %s".formatted(currency));
    }
}

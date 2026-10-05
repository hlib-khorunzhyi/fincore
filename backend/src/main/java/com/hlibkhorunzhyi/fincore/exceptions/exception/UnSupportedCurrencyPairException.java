package com.hlibkhorunzhyi.fincore.exceptions.exception;

import com.hlibkhorunzhyi.fincore.account.entity.Currency;

public class UnSupportedCurrencyPairException extends RuntimeException {
    public UnSupportedCurrencyPairException(Currency sourceCurrency, Currency destinationCurrency) {
        super("Currency exchange from %s to %s is not supported".formatted(sourceCurrency, destinationCurrency));
    }
}

package com.hlibkhorunzhyi.fincore.exchange.service;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.Currency;
import com.hlibkhorunzhyi.fincore.exceptions.exception.InvalidExchangeAmountException;
import com.hlibkhorunzhyi.fincore.exceptions.exception.UnSupportedCurrencyPairException;
import com.hlibkhorunzhyi.fincore.exchange.model.ExchangeResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Override
    public ExchangeResult calculate(Account sourceAccount, Account destinationAccount, BigDecimal amount) {

        Currency sourceCurrency = sourceAccount.getCurrency();
        Currency destinationCurrency = destinationAccount.getCurrency();

        if (sourceCurrency == destinationCurrency)
            return new ExchangeResult(amount, BigDecimal.ONE);

        BigDecimal exchangeRate = null;

        // test logic
        if (sourceCurrency == Currency.USD && destinationCurrency == Currency.EUR)
            exchangeRate = BigDecimal.valueOf(0.89);
        else if (sourceCurrency == Currency.EUR && destinationCurrency == Currency.USD)
            exchangeRate = BigDecimal.valueOf(1.13);
        else {
            throw new UnSupportedCurrencyPairException(
                    sourceCurrency,
                    destinationCurrency
            );
        }


        BigDecimal destinationAmount = roundAmount(amount.multiply(exchangeRate), destinationCurrency);

        if (destinationAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidExchangeAmountException(destinationCurrency);

        return new ExchangeResult(destinationAmount, exchangeRate);
    }

    private BigDecimal roundAmount(BigDecimal amount, Currency currency) {
        return amount.setScale(
                currency.getScale(),
                RoundingMode.HALF_UP
        );
    }
}

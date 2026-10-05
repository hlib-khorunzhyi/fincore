package com.hlibkhorunzhyi.fincore.exchange.service;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.exchange.model.ExchangeResult;

import java.math.BigDecimal;

public interface ExchangeRateService {

    ExchangeResult calculate(Account sourceAccount, Account destinationAccount, BigDecimal amount);
}

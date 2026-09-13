package com.hlibkhorunzhyi.fincore.account.dto;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import com.hlibkhorunzhyi.fincore.account.entity.AccountStatus;
import com.hlibkhorunzhyi.fincore.account.entity.Currency;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String accountNumber,
        BigDecimal balance,
        Currency currency,
        AccountStatus status
) {

    public static AccountResponse from(Account account){
        return new AccountResponse(account.getId(), account.getAccountNumber(), account.getBalance(), account.getCurrency(), account.getStatus());
    }
}

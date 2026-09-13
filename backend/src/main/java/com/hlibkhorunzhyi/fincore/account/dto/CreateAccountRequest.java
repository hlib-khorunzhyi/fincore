package com.hlibkhorunzhyi.fincore.account.dto;

import com.hlibkhorunzhyi.fincore.account.entity.Currency;

public record CreateAccountRequest(
        Long userId,
        Currency currency
) {
}

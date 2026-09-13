package com.hlibkhorunzhyi.fincore.account.dto;

import com.hlibkhorunzhyi.fincore.account.entity.Currency;
import jakarta.validation.constraints.NotNull;

public record CreateAccountRequest(

        @NotNull
        Long userId,

        @NotNull
        Currency currency
) {
}

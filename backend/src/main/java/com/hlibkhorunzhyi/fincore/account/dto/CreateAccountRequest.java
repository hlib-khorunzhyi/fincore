package com.hlibkhorunzhyi.fincore.account.dto;

import com.hlibkhorunzhyi.fincore.account.entity.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CreateAccountRequest(

        @NotNull(message = "Currency is required")
        Currency currency
) {
}

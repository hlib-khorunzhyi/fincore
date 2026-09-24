package com.hlibkhorunzhyi.fincore.transfer.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(

        @NotNull
        Long sourceAccountId,

        @NotNull
        Long destinationAccountId,

        @NotNull
        @Positive
        @Digits(integer = 17, fraction = 2)
        BigDecimal amount
) {
}

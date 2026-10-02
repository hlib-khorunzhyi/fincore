package com.hlibkhorunzhyi.fincore.transfer.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

public record TransferRequest(

        @NonNull
        @NotBlank
        String sourceAccountNumber,

        @NotNull
        @NotBlank
        String destinationAccountNumber,

        @NotNull
        @Positive
        @Digits(integer = 17, fraction = 2)
        BigDecimal amount
) {
}

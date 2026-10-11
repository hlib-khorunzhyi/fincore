package com.hlibkhorunzhyi.fincore.transfer.dto;

import com.hlibkhorunzhyi.fincore.transfer.entity.Transfer;
import com.hlibkhorunzhyi.fincore.transfer.entity.TransferStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferResponse(
        String sourceAccountNumber,
        BigDecimal sourceAmount,
        String destinationAccountNumber,
        BigDecimal destinationAmount,
        BigDecimal feeAmount,
        BigDecimal exchangeRate,
        TransferStatus status,
        Instant createdAt
) {

    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
                transfer.getSourceAccount().getAccountNumber(),
                transfer.getSourceAmount(),
                transfer.getDestinationAccount().getAccountNumber(),
                transfer.getDestinationAmount(),
                transfer.getFeeAmount(),
                transfer.getExchangeRate(),
                transfer.getStatus(),
                transfer.getCreatedAt()
        );
    }
}

package com.hlibkhorunzhyi.fincore.transfer.dto;

import com.hlibkhorunzhyi.fincore.transfer.entity.Transfer;
import com.hlibkhorunzhyi.fincore.transfer.entity.TransferStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record TransferResponse(
        String sourceAccountNumber,
        String destinationAccountNumber,
        BigDecimal amount,
        TransferStatus status,
        Instant createdAt
) {

    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(
                transfer.getSourceAccount().getAccountNumber(),
                transfer.getDestinationAccount().getAccountNumber(),
                transfer.getDestinationAmount(),
                transfer.getStatus(),
                transfer.getCreatedAt()
        );
    }
}

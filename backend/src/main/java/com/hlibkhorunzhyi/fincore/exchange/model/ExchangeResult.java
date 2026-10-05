package com.hlibkhorunzhyi.fincore.exchange.model;

import java.math.BigDecimal;

public record ExchangeResult(
        BigDecimal destinationAmount,
        BigDecimal exchangeRate
) {
}

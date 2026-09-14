package com.hlibkhorunzhyi.fincore.exceptions.dto;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp
) {
}

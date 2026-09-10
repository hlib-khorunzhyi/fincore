package com.hlibkhorunzhyi.fincore.auth.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
}

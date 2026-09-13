package com.hlibkhorunzhyi.fincore.auth.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

        @NotNull
        String email,

        @NotNull
        String firstName,

        @NotNull
        String lastName,

        @NotNull
        String password) {
}

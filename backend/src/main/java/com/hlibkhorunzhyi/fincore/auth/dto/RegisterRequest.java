package com.hlibkhorunzhyi.fincore.auth.dto;

public record RegisterRequest(String email, String firstName, String lastName, String password) {
}

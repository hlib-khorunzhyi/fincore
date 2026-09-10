package com.hlibkhorunzhyi.fincore.user.dto;

public record CreateUserRequest(String email, String firstName, String lastName, String password) {
}

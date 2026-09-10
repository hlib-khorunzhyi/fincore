package com.hlibkhorunzhyi.fincore.user.dto;

import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.entity.UserStatus;

public record UserResponse(Long id, String firstName, String lastName, UserStatus status) {

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getStatus());
    }
}

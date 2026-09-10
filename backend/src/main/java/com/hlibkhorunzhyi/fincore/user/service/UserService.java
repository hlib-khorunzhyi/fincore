package com.hlibkhorunzhyi.fincore.user.service;

import com.hlibkhorunzhyi.fincore.user.dto.CreateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UpdateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UserResponse;
import com.hlibkhorunzhyi.fincore.user.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse getUserById(Long id);

    User getUserByEmail(String email);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}

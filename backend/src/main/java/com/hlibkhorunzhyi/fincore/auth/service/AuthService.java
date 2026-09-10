package com.hlibkhorunzhyi.fincore.auth.service;

import com.hlibkhorunzhyi.fincore.auth.dto.AuthResponse;
import com.hlibkhorunzhyi.fincore.auth.dto.LoginRequest;
import com.hlibkhorunzhyi.fincore.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}

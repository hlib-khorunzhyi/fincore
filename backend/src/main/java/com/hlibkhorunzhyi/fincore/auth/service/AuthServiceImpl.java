package com.hlibkhorunzhyi.fincore.auth.service;

import com.hlibkhorunzhyi.fincore.auth.dto.AuthResponse;
import com.hlibkhorunzhyi.fincore.auth.dto.LoginRequest;
import com.hlibkhorunzhyi.fincore.auth.dto.RegisterRequest;
import com.hlibkhorunzhyi.fincore.exceptions.exception.InvalidCredentialsException;
import com.hlibkhorunzhyi.fincore.security.JwtService;
import com.hlibkhorunzhyi.fincore.user.dto.CreateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UserResponse;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        UserResponse user = userService.createUser(
                new CreateUserRequest(
                        request.email(),
                        request.firstName(),
                        request.lastName(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(user.id());
        return new AuthResponse(token, "Bearer");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userService.getUserByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash()))
            throw new InvalidCredentialsException();

        String token = jwtService.generateToken(user.getId());

        return new AuthResponse(token, "Bearer");
    }
}

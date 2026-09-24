package com.hlibkhorunzhyi.fincore.user.service;

import com.hlibkhorunzhyi.fincore.exceptions.exception.EmailAlreadyExistsException;
import com.hlibkhorunzhyi.fincore.exceptions.exception.UserNotFoundException;
import com.hlibkhorunzhyi.fincore.user.dto.CreateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UserResponse;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.entity.UserStatus;
import com.hlibkhorunzhyi.fincore.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUser() {

        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "test@gmail.com",
                "testFirstName",
                "testLastName",
                "password123"
        );

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPasswordHash("encoded-password");

        when(userRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.password())).thenReturn("encoded-password");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponse response = userService.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("testFirstName", response.firstName());
        assertEquals("testLastName", response.lastName());

        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("test@gmail.com", capturedUser.getEmail());
        assertEquals("testFirstName", capturedUser.getFirstName());
        assertEquals("testLastName", capturedUser.getLastName());
        assertEquals("encoded-password", capturedUser.getPasswordHash());
    }

    @Test
    void shouldGetUserById() {

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse expectedResponse = new UserResponse(
                1L,
                "testFirstName",
                "testLastName",
                UserStatus.ACTIVE
        );

        // Act
        UserResponse response = userService.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldGetUserByEmail() {

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        // Act
        User actualUser = userService.getUserByEmail("test@gmail.com");

        // Assert
        assertNotNull(actualUser);
        assertEquals(user, actualUser);
        verify(userRepository).findByEmail("test@gmail.com");
    }

    @Test
    void shouldDeleteUser(){

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenNotFound(){

        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User with id '1' not found", exception.getMessage());
        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists(){

        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "test@gmail.com",
                "testFirstName",
                "testLastName",
                "password123"
        );

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        // Act
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });

        // Assert
        assertEquals("User with email 'test@gmail.com' already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}

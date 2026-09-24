package com.hlibkhorunzhyi.fincore.user.controller;

import com.hlibkhorunzhyi.fincore.exceptions.exception.EmailAlreadyExistsException;
import com.hlibkhorunzhyi.fincore.exceptions.exception.UserNotFoundException;
import com.hlibkhorunzhyi.fincore.security.SecurityConfig;
import com.hlibkhorunzhyi.fincore.security.handler.CustomAccessDeniedHandler;
import com.hlibkhorunzhyi.fincore.security.handler.JwtAuthenticationEntryPoint;
import com.hlibkhorunzhyi.fincore.security.jwt.JwtService;
import com.hlibkhorunzhyi.fincore.user.dto.CreateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UserResponse;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.entity.UserRole;
import com.hlibkhorunzhyi.fincore.user.entity.UserStatus;
import com.hlibkhorunzhyi.fincore.user.repository.UserRepository;
import com.hlibkhorunzhyi.fincore.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockitoBean
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Test
    @WithMockUser
    void shouldGetUsers() throws Exception {

        List<UserResponse> users = List.of(
                new UserResponse(1L, "John", "Doe", UserStatus.ACTIVE),
                new UserResponse(2L, "Will", "Smith", UserStatus.ACTIVE)
        );

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));

        verify(userService).getUsers();
    }

    @Test
    void shouldGetMe() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setRole(UserRole.USER);

        UserResponse response = new UserResponse(
                1L,
                "John",
                "Doe",
                UserStatus.ACTIVE
        );

        when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/me").with(user(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService).getUserById(1L);
    }

    @Test
    @WithMockUser
    void shouldCreateUser() throws Exception {

        CreateUserRequest request = new CreateUserRequest(
                "test@gmail.com",
                "John",
                "Doe",
                "password123"
        );

        UserResponse response = new UserResponse(
                1L,
                "John",
                "Doe",
                UserStatus.ACTIVE
        );

        when(userService.createUser(request)).thenReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content("""
                                {
                                    "email": "test@gmail.com",
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService).createUser(request);
    }

//    @Test
//    void shouldReturnBadRequestWhenCreateUserIsInvalid() throws Exception {
//
//        CreateUserRequest request = new CreateUserRequest(
//                "test@gmail.com",
//                "John",
//                "Doe",
//                "password123"
//        );
//
//        when(userService.createUser(request)).thenThrow(new EmailAlreadyExistsException("test@gmail.com"));
//
//    }

    @Test
    @WithMockUser
    void shouldGetUserById() throws Exception {
        UserResponse response = new UserResponse(
                1L,
                "John",
                "Doe",
                UserStatus.ACTIVE
        );

        when(userService.getUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(userService).getUserById(1L);
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {

        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("User with id '1' not found"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        verify(userService).getUserById(1L);
    }

    @Test
    @WithMockUser
    void shouldDeleteUserById() throws Exception {

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(userService).deleteUser(1L);
    }

}

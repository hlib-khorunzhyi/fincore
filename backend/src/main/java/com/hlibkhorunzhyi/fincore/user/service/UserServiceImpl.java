package com.hlibkhorunzhyi.fincore.user.service;

import com.hlibkhorunzhyi.fincore.user.dto.CreateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UpdateUserRequest;
import com.hlibkhorunzhyi.fincore.user.dto.UserResponse;
import com.hlibkhorunzhyi.fincore.user.entity.User;
import com.hlibkhorunzhyi.fincore.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> getUsers(){
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponseList = new ArrayList<>();
        for(User user: userList){
            userResponseList.add(UserResponse.from(user));
        }
        return userResponseList;

//        cleaner version with stream
//        return userRepository.findAll().stream().map(UserResponse::from).toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
//                .orElseThrow(() -> new UserNotFoundException);

        return UserResponse.from(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
//                .orElseThrow(() -> new UserNotFoundException);

        return user;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = new User();

        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        String passwordHash = passwordEncoder.encode(request.password());
        user.setPasswordHash(passwordHash);

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

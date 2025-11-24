package com.example.project.user.service;

import com.example.project.common.entity.User;
import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest request) {

        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getNickname(),
                request.getPassword()
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponse.from(savedUser);

    }
}

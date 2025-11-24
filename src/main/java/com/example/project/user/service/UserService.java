package com.example.project.user.service;

import com.example.project.common.entity.User;
import com.example.project.common.utils.PasswordEncoder;
import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.request.UpdateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.model.response.GetUserResponse;
import com.example.project.user.model.response.UpdateUserResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponse createUser(CreateUserRequest request) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUserName(),
                request.getEmail(),
                request.getNickname(),
                encodedPassword
        );

        User savedUser = userRepository.save(user);

        return CreateUserResponse.from(savedUser);

    }

    @Transactional(readOnly = true)
    public GetUserResponse findUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저 없음")
        );

        return GetUserResponse.from(user);
    }

    // 내 정보 수정
    @Transactional
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("유저 없음")
        );

        user.updateUser(
                request.getNickname(),
                request.getPassword()
        );

        return UpdateUserResponse.from(user);
    }
}

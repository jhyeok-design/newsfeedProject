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
    public GetUserResponse findUser(Long userId) {
        User user = findUserOrException(userId);

        return GetUserResponse.from(user);
    }

    // 내 정보 수정 (로그인 기능 적용 전까지 userId 임시 사용)
    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = findUserOrException(userId);

        user.updateUser(
                request.getNickname(),
                request.getPassword()
        );

        return UpdateUserResponse.from(user);
    }

    // 회원 삭제 (로그인 기능 적용 전까지 userId 임시 사용)
    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserOrException(userId);

        userRepository.deleteById(userId);
    }

    public User findUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("유저 없음")
        );
    }
}


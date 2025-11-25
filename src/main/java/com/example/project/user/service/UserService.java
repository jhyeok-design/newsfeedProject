package com.example.project.user.service;

import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.common.utils.PasswordEncoder;
import com.example.project.security.jwt.JwtUtil;
import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.request.LoginRequest;
import com.example.project.user.model.request.UpdateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.model.response.GetUserResponse;
import com.example.project.user.model.response.LoginResponse;
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
    private final JwtUtil jwtUtil;

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

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String token = jwtUtil.generateToken(user.getId());
        return new LoginResponse(token);
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

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.SAME_PASSWORD);
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.updateUser(
                request.getNickname(),
                encodedNewPassword
        );

        return UpdateUserResponse.from(user);
    }

    // 회원 삭제 (로그인 기능 적용 전까지 userId 임시 사용)
    @Transactional
    public void deleteUser(Long userId, String rawPassword) {

        User user = findUserOrException(userId);

        String encodedPassword = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        //userRepository.delete(user);
        user.softDelete();
    }

    public User findUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }
}


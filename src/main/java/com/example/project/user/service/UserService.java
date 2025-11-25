package com.example.project.user.service;

import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.common.utils.PasswordEncoder;
import com.example.project.security.jwt.JwtUtil;
import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.request.DeleteUserRequest;
import com.example.project.user.model.request.LoginRequest;
import com.example.project.user.model.request.UpdateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.model.response.GetUserResponse;
import com.example.project.user.model.response.LoginResponse;
import com.example.project.user.model.response.UpdateUserResponse;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token);
    }

    // 로그아웃
    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        user.increaseTokenVersion();
    }

    @Transactional(readOnly = true)
    public GetUserResponse findUser(Long userId) {
        User user = findUserOrException(userId);

        return GetUserResponse.from(user);
    }

    // 내 정보 수정
    @Transactional
    public UpdateUserResponse updateMe(Long userId, UpdateUserRequest request) {
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
    // 기존 : @PathVariable로 userId를 받아서 findById후 encodedPassword로 해당 유저의 인코딩된 비밀번호를 특정해서 대조함
    // 변경 : @PathVariable대신 JwtAuthenticationFilter의 SecurityContext에서 UserId를 가져와서 대조 (서비스 하단 메서드)
    @Transactional
    public void deleteUser(DeleteUserRequest request) {

        Long userId = getCurrentUserId();
        User user = findUserOrException(userId);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
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

    // SecurityContextHolder - 현재 로그인한 userId 가져오기
    private Long getCurrentUserId() {
        return (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}


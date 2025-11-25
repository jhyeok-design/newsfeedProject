package com.example.project.user.controller;

import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.request.DeleteUserRequest;
import com.example.project.user.model.request.LoginRequest;
import com.example.project.user.model.request.UpdateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.model.response.GetUserResponse;
import com.example.project.user.model.response.LoginResponse;
import com.example.project.user.model.response.UpdateUserResponse;
import com.example.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {
        LoginResponse result = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        userService.logout(userId);
        SecurityContextHolder.clearContext();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 유저 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(
            @PathVariable Long userId) {
        GetUserResponse result = userService.findUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 마이 페이지 조회
    @GetMapping("/users/me")
    public ResponseEntity<GetUserResponse> getMe() {
        GetUserResponse result = userService.getMe();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 내 정보 수정
    @PatchMapping("/users/me")
    public ResponseEntity<UpdateUserResponse> updateMe(
            @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserResponse result = userService.updateMe(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 회원정보 논리적 삭제
    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

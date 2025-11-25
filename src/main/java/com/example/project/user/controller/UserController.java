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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse result = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        LoginResponse result = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(
            @PathVariable long userId) {
        GetUserResponse result = userService.findUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 내 정보 수정
    @PatchMapping("/users/me")
    public ResponseEntity<UpdateUserResponse> updateMe(
            @Valid @RequestBody UpdateUserRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getPrincipal();

        UpdateUserResponse result = userService.updateMe(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 회원 탈퇴
    @DeleteMapping("/users/me")
    public ResponseEntity<Void> deleteUser(
            @Valid @RequestBody DeleteUserRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getPrincipal();

        userService.deleteUser(userId, request.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

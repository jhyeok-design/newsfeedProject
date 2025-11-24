package com.example.project.user.controller;

import com.example.project.user.model.request.CreateUserRequest;
import com.example.project.user.model.response.CreateUserResponse;
import com.example.project.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(
            @RequestBody CreateUserRequest request
    ) {
        CreateUserResponse result = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}

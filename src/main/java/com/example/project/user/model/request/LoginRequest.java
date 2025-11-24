package com.example.project.user.model.request;

import lombok.Getter;

@Getter
public class LoginRequest {

    // TODO: Validation 설정 필요
    private String email;
    private String password;
}

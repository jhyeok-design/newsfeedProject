package com.example.project.user.model.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String userName;
    private String email;
    private String nickname;
    private String password;
}

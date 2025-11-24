package com.example.project.user.model.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String email;
    private String nickname;
    private String userName;
    private String password;
}

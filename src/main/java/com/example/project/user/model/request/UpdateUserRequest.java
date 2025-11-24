package com.example.project.user.model.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String email;
    private String nickname;
    private String password;
}

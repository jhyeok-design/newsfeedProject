package com.example.project.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequest {

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}

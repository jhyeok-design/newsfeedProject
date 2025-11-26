package com.example.project.user.model.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

//    @NotBlank(message = "email형식이 필요합니다.")
//    @Email(message = "올바른 이메일 형식이 아닙니다.")
//    private String email;
    private String nickname;

//    @NotBlank(message = "실명은 필수입니다.")
//    @Size(max = 4, message = "실명은 4자 이내여야 합니다.")
//    private String userName;
    private String currentPassword;
    private String newPassword;
}

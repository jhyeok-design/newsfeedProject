package com.example.project.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 3, max = 20, message = "닉네임은 3~20자여야 합니다.")
    @Pattern(
            regexp = "^[a-z][a-z0-9_]*$",
            message = "닉네임은 영문 소문자로 시작하고 숫자와 '_'만 포함할 수 있습니다.")
    private String nickname;

    @NotBlank(message = "실명은 필수입니다.")
    @Size(max = 4, message = "실명은 4자 이내여야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @Pattern(
            regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$",
            message = "비밀번호는 공백을 포함할 수 없으며 대문자, 소문자, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다.")
    private String password;
}

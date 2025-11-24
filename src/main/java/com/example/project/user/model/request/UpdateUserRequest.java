package com.example.project.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

//    @NotBlank(message = "email형식이 필요합니다.")
//    @Email(message = "올바른 이메일 형식이 아닙니다.")
//    private String email;

    @NotBlank(message = "별명은 필수입니다.")
    @Size(max = 20, message = "별명은 20자 이내여야 합니다.")
    @Pattern(
            regexp = "^[a-z][a-z0-9_]{2,19}$",
            message = "별명은 영문 소문자 시작, 숫자와 '_'만 가능하며 3~20자여야 합니다."
    )
    private String nickname;

//    @NotBlank(message = "실명은 필수입니다.")
//    @Size(max = 4, message = "실명은 4자 이내여야 합니다.")
//    private String userName;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호가 너무 짧습니다.")
    private String password;
}

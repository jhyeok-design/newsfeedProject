package com.example.project.common.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank(message = "userName은 필수입니다.")
    @Pattern(
            regexp = "^[a-z][a-z0-9_]{2,19}$",
            message = "유저명은 영문 소문자 시작, 숫자와 '_'만 가능하며 3~20자여야 합니다."
    )
    private String userName;

    @Column(unique = true)
    @NotBlank(message = "email형식이 필요합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "별명은 필수입니다.")
    @Size(max = 20, message = "별명은 20자 이내여야 합니다.")
    @Pattern(regexp = "^[^\\s]+$", message = "별명에는 공백이 포함될 수 없습니다.")
    private String nickname;

    private String password;


    public User(String userName, String email, String nickname, String password) {
        this.userName = userName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    // 유저의 닉네임, 비밀번호 수정
    public void updateUser(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}

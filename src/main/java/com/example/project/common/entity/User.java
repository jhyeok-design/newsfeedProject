package com.example.project.common.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    // 회원정보 논리적 삭제 플레그
    private boolean deleted = false;
    // 삭제 시간 기록용
    private LocalDateTime deletedAt;

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

    // 회원정보 논리적 삭제 + 시간 기록
    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}

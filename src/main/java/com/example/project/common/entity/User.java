package com.example.project.common.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
// 논리적 삭제 처리 -> 조회 불가능
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    // 회원정보 논리적 삭제 플레그
    @Column(nullable = false)
    private boolean deleted = false;
    // 삭제 시간 기록용
    private LocalDateTime deletedAt;

    // 로그아웃 시 토큰 버전 저장
    @Column(nullable = false)
    private Integer tokenVersion = 0;

    public User(String userName, String email, String nickname, String password) {
        this.userName = userName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    // 유저의 닉네임 수정
    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    // 유저의 비밀번호 수정
    public void modifyPassword(String encodedPassword) {
        this.password = encodedPassword;
        this.increaseTokenVersion();
    }

    // 회원정보 논리적 삭제 + 시간 기록
    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // 토큰 버전 증가
    public void increaseTokenVersion() {
        this.tokenVersion++;
    }
}

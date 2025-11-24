package com.example.project.common.entity;


import jakarta.persistence.*;
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
    private String userName;
    private String email;
    private String nickname;
    private String password;


    public User(String userName, String email, String nickname, String password) {
        this.userName = userName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}

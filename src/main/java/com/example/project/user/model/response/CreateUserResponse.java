package com.example.project.user.model.response;

import com.example.project.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateUserResponse {

    private final long id;
    private final String username;
    private final String email;
    private final String nickname;
    private final LocalDateTime createdAt;


    public static CreateUserResponse from(User user) {
        return new CreateUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserName(),
                user.getCreatedAt()
        );
    }
}

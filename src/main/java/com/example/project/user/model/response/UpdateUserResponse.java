package com.example.project.user.model.response;

import com.example.project.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateUserResponse {

    private final long id;
    private final String email;
    private final String nickname;
    private final String userName;
    private final LocalDateTime modifiedAt;


    public static UpdateUserResponse from(User user) {
        return new UpdateUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserName(),
                user.getModifiedAt()
        );
    }
}

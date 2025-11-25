package com.example.project.user.model.response;

import com.example.project.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetOtherUserResponse {

    private final long id;
    private final String email;
    private final String nickname;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public static GetOtherUserResponse from(User user) {
        return new GetOtherUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserName(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }


}

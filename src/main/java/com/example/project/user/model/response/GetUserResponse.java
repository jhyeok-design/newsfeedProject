package com.example.project.user.model.response;

import com.example.project.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetUserResponse {

    private final long id;
    private final String email;
    private final String nickname;
    private final String userName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private final int followerCount;
    private final int followingCount;


    public static GetUserResponse from(User user, int followerCount, int followingCount) {
        return new GetUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserName(),
                user.getCreatedAt(),
                user.getModifiedAt(),

                followerCount,
                followingCount
        );
    }


}

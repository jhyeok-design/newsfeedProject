package com.example.project.user.model.response;

import com.example.project.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetOtherUserResponse {

    private final long id;
    private final String nickname;
    private final int followerCount;
    private final int followingCount;

    public static GetOtherUserResponse from(User user, int followerCount, int followingCount) {
        return new GetOtherUserResponse(
                user.getId(),
                user.getNickname(),
                followerCount,
                followingCount
        );
    }

}

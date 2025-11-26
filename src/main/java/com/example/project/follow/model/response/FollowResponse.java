package com.example.project.follow.model.response;

import com.example.project.common.entity.Follow;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 팔로링, 팔로워 목록 응답
@Getter
@RequiredArgsConstructor
public class FollowResponse {

    private final Long followId;
    private final String nickname;

    public static FollowResponse fromFollowers(Follow follow){
        return new FollowResponse(
                follow.getFollowers().getId(),
                follow.getFollowers().getNickname());
    }

    public static FollowResponse fromFollowings(Follow follow){
        return new FollowResponse(
                follow.getFollowings().getId(),
                follow.getFollowings().getNickname());
    }
}

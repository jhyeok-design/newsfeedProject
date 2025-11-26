package com.example.project.follow.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// 팔로우 대상 요청
@Getter
public class FollowRequest {

    @NotNull
    private Long followingId;
}

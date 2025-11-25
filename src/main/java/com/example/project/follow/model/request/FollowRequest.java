package com.example.project.follow.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

// 팔로우 대상 요청
@Getter
public class FollowRequest {

    @NotBlank
    private Long followingId;
}

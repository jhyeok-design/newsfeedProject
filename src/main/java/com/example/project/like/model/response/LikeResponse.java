package com.example.project.like.model.response;

import lombok.Getter;

@Getter
public class LikeResponse {

    private final Long id;
    private final Long postId;
    private final Long userId;

    public LikeResponse(Long id, Long postId, Long userId) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
    }
}

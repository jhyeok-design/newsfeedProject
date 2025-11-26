package com.example.project.like.model.response;

import lombok.Getter;

@Getter
public class LikeResponse {

    private final Long postId;
    private final long likeCount;
    private final boolean liked;

    public LikeResponse(Long postId, long likeCount, boolean liked) {
        this.postId = postId;
        this.likeCount = likeCount;
        this.liked = liked;
    }
}

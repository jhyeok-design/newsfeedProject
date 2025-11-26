package com.example.project.post.dto;

import com.example.project.common.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreatePostResponse {
    private final long id;
    private final String title;
    private final String content;
    private final long userId;
    private final String userNickname;
    private final long likeCount;
    private final long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static CreatePostResponse from(Post post) {
        return new CreatePostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getId(),
                post.getUser().getNickname(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}

package com.example.project.post.dto;

import com.example.project.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdatePostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UpdatePostResponse from(Post post) {
        return new UpdatePostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUserId(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}

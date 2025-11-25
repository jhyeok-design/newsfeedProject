package com.example.project.post.dto;

import com.example.project.common.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadPostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final String userNickname;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static ReadPostResponse from(Post post) {
        return new ReadPostResponse(
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

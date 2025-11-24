package com.example.project.post.dto;

import com.example.project.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdatePostResponse {
    // 속성
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    // 생성자

    // 기능
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
public class UpdatePostResponse {
}

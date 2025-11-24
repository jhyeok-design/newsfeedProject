package com.example.project.post.dto;
import com.example.project.post.entity.Post;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReadPostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final Long likeCount;
    private final Long commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ReadPostResponse (Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUserId();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}

package com.example.project.post.dto;

import com.example.project.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String content;

    @Builder
    public CreatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}

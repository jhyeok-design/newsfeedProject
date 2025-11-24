package com.example.project.post.dto;

import com.example.project.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title;
    private String content;
}

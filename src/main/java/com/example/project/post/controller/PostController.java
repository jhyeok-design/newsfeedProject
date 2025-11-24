package com.example.project.post.controller;

import com.example.project.post.dto.CreatePostRequest;
import com.example.project.post.dto.CreatePostResponse;
import com.example.project.post.entity.Post;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

        Post post = request.toEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(post));
    }
}

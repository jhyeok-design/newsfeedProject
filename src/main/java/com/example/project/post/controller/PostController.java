package com.example.project.post.controller;

import com.example.project.post.dto.CreatePostResponse;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

}

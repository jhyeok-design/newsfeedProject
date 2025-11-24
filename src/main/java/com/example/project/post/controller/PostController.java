package com.example.project.post.controller;

import com.example.project.post.dto.ReadPostResponse;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/me/{userID}")
    public ResponseEntity<List<ReadPostResponse>> getAllPostMe(
            @PathVariable Long userID
    ) {
        List<ReadPostResponse> result = postService.getAllMe(userID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

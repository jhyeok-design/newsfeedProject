package com.example.project.like.controller;

import com.example.project.like.model.response.LikeResponse;
import com.example.project.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.project.security.util.SecurityUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 좋아요 생성
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeResponse> createLike(
            @PathVariable Long postId) {
        Long currentUserId = getCurrentUserId();
        LikeResponse result = likeService.like(currentUserId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 좋아요 취소
    @DeleteMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeResponse> deleteLike(
            @PathVariable Long postId) {
        Long currentUserId = getCurrentUserId();
        LikeResponse result = likeService.unlike(currentUserId, postId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

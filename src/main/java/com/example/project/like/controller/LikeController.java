package com.example.project.like.controller;

import com.example.project.like.model.response.LikeResponse;
import com.example.project.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 토큰 로그인 유저 가져오기
    private Long getCurrentUserId() {
        return (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

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

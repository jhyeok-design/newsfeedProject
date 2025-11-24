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

/**
 * 게시물 관련 요청을 받아 처리하는 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 내 게시물 전체 조회
     * @param userID 유저 ID
     * @return 조회된 게시물의 Response DTO의 리스트
     */
    @GetMapping("users/{userID}/posts/me")
    public ResponseEntity<List<ReadPostResponse>> getAllPostMe(
            @PathVariable Long userID
    ) {
        List<ReadPostResponse> result = postService.getAllMe(userID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 단건 조회
     * @param userID 로그인한 유저 ID
     * @param postID 조회할 게시물 ID
     * @return 조회된 게시물의 Response DTO
     */
    @GetMapping("users/{userID}/posts/{postID}")
    public ResponseEntity<ReadPostResponse> getOnePost(
            @PathVariable Long userID,
            @PathVariable Long postID
    ) {
        ReadPostResponse result = postService.getOne(userID, postID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

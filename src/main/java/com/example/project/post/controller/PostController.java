package com.example.project.post.controller;

import com.example.project.post.dto.CreatePostResponse;
import com.example.project.post.dto.ReadPostResponse;
import com.example.project.post.dto.CreatePostRequest;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 게시물 관련 요청을 받아 처리하는 Controller 클래스
 */
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시물 생성
     * @param request CreatePostRequest DTO(생성할 게시물의 제목과 내용)
     * @return 생성된 게시물의 Response DTO
     */
    @PostMapping("/posts")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 내 게시물 전체 조회
     * @param userID 유저 ID
     * @return 조회된 게시물의 Response DTO의 리스트, 200(OK) 상태 코드
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
     * @return 조회된 게시물의 Response DTO, 200(OK) 상태 코드
     */
    @GetMapping("users/{userID}/posts/{postID}")
    public ResponseEntity<ReadPostResponse> getOnePost(
            @PathVariable Long userID,
            @PathVariable Long postID
    ) {
        ReadPostResponse result = postService.getOne(userID, postID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 삭제
     * @param userID 로그인한 유저 ID
     * @param postID 조회한 게시물 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("users/{userID}/posts/{postID}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long userID,
            @PathVariable Long postID
    ) {
        postService.delete(userID, postID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

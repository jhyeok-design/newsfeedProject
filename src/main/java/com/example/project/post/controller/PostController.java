package com.example.project.post.controller;

import com.example.project.post.dto.*;
import com.example.project.post.dto.ReadPostResponse;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

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
        CreatePostResponse result = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 게시물 전체 조회
     * - 쿼리 파라미터로 유저 ID를 받아, 특정 유저의 전체 게시물 조회 가능
     * @return 조회된 게시물 DTO의 리스트
     */
    @GetMapping("/posts")
    public ResponseEntity<List<ReadPostResponse>> getAllPost(
            @RequestParam(name = "userId", required = false) Long userId) {

        List<ReadPostResponse> result = postService.getAllPost(userId, Pageable.unpaged()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 전체 조회 페이징
     * @param page 보려는 페이지
     * @param size 한번에 보려는 게시물 수
     * @return 조회된 게시물 DTO가 모여있는 페이지
     */
    @GetMapping("/posts/pages")
    public ResponseEntity<Page<ReadPostResponse>> getAllPostPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ReadPostResponse> result = postService.getAllPost(null, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 단건 조회
     * @param postID 조회할 게시물 ID
     * @return 조회된 게시물의 Response DTO, 200(OK) 상태 코드
     */
    @GetMapping("/posts/{postID}")
    public ResponseEntity<ReadPostResponse> getOnePost(@PathVariable Long postID) {
        ReadPostResponse result = postService.getOnePost(postID);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 수정
     * @param postId 게시물 ID
     * @param request 수정할 게시물 정보 ReadResponse DTO
     * @return 수정된 게시물의 Response DTO
     */
    @PutMapping("/posts/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable Long postId,
                                                         @RequestBody UpdatePostRequest request) {
        UpdatePostResponse result = postService.updatePost(postId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 삭제
     * @param postID 조회한 게시물 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("/posts/{postID}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postID) {
        postService.deletePost(postID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

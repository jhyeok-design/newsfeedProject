package com.example.project.post.controller;

import com.example.project.post.dto.*;
import com.example.project.post.dto.ReadPostResponse;
import com.example.project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import static com.example.project.security.util.SecurityUtil.getCurrentUserId;

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
        Long userId = getCurrentUserId();
        CreatePostResponse result = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 게시물 전체 조회 (페이징)
     * - 특정 유저의 전체 게시물 조회 가능
     * - 기간 별 게시물 조회 가능
     * @param userId 조회할 유저 Id (선택)
     * @param startDate 시작일 (선택)
     * @param endDate 종료일 (선택)
     * @param page 보려는 페이지
     * @param size 한번에 보려는 게시물 수
     * @return 조회한 게시물이 있는 페이지
     */
    @GetMapping("/posts/pages")
    public ResponseEntity<Page<ReadPostResponse>> getAllPostPage(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ReadPostResponse> result = postService.getAllPost(userId, startDate, endDate, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 내가 팔로우 한 유저들의 게시물 전체 조회 (페이징)
     * @param page 보려는 페이지
     * @param size 한번에 보려는 게시물 수
     * @return 조회한 게시물이 있는 페이지
     */
    @GetMapping("/posts/followers/pages")
    public ResponseEntity<Page<ReadPostResponse>> getFollowerPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<ReadPostResponse> result = postService.getFollowerPost(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 단건 조회
     * @param postId 조회할 게시물 Id
     * @return 조회된 게시물의 Response DTO, 200(OK) 상태 코드
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ReadPostResponse> getOnePost(@PathVariable Long postId) {
        ReadPostResponse result = postService.getOnePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 수정
     * @param postId 게시물 Id
     * @param request 수정할 게시물 정보 ReadResponse DTO
     * @return 수정된 게시물의 Response DTO
     */
    @PatchMapping("/posts/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable Long postId,
                                                         @RequestBody UpdatePostRequest request) {
        Long userId = getCurrentUserId();
        UpdatePostResponse result = postService.updatePost(userId, postId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 게시물 삭제
     * @param postId 조회한 게시물 ID
     * @return 204(NO_CONTENT) 상태 코드
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        Long userId = getCurrentUserId();
        postService.deletePost(userId, postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

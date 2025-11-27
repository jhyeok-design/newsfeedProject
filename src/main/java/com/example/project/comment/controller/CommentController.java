package com.example.project.comment.controller;

import com.example.project.comment.model.request.CreateCommentRequest;
import com.example.project.comment.model.request.UpdateCommentRequest;
import com.example.project.comment.model.response.CreateCommentResponse;
import com.example.project.comment.model.response.GetCommentResponse;
import com.example.project.comment.model.response.UpdateCommentResponse;
import com.example.project.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.project.security.util.SecurityUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request) {
        Long currentUserId = getCurrentUserId();
        CreateCommentResponse result = commentService.comment(currentUserId, postId, request.getComment());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<Page<GetCommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<GetCommentResponse> result = commentService.findComments(postId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request) {
        Long currentUserId = getCurrentUserId();
        UpdateCommentResponse result = commentService.update(currentUserId, commentId, request.getNewComment());
        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId) {
        Long currentUserId = getCurrentUserId();
        commentService.delete(currentUserId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

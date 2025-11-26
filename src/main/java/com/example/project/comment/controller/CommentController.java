package com.example.project.comment.controller;

import com.example.project.comment.model.request.CreateCommentRequest;
import com.example.project.comment.model.response.CreateCommentResponse;
import com.example.project.comment.model.response.GetCommentResponse;
import com.example.project.comment.service.CommentService;
import com.example.project.like.model.response.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.project.security.util.SecurityUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class CommentController  {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request) {
        Long currentUserId = getCurrentUserId();
        CreateCommentResponse result = commentService.comment(currentUserId, postId, request.getComment());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<GetCommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<GetCommentResponse> result = commentService.findComments(postId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }




}

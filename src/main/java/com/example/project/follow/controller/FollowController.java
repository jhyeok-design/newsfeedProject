package com.example.project.follow.controller;

import com.example.project.follow.model.request.FollowRequest;
import com.example.project.follow.model.response.FollowResponse;
import com.example.project.follow.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.project.security.util.SecurityUtil.getCurrentUserId;


@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;

    // 팔로우하기
    @PostMapping
    public ResponseEntity<Void> saveFollow(
            @Valid @RequestBody FollowRequest request) {
        Long currentUserId = getCurrentUserId();
        followService.follow(currentUserId, request.getFollowingId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 팔로우취소
    @DeleteMapping("/{followingId}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable Long followingId) {
        Long currentUserId = getCurrentUserId();
        followService.unfollow(currentUserId, followingId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 팔로잉 조회
    @GetMapping("/followings")
    public ResponseEntity<Page<FollowResponse>> getFollowings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long currentUserId = getCurrentUserId();
        Page<FollowResponse> result = followService.findFollowings(currentUserId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 팔로워 조회
    @GetMapping("/followers")
    public ResponseEntity<Page<FollowResponse>> getFollowers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long currentUserId = getCurrentUserId();
        Page<FollowResponse> result = followService.findFollowers(currentUserId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}

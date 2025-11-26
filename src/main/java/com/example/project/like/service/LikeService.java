package com.example.project.like.service;

import com.example.project.common.entity.Like;
import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.like.repository.LikeRepository;
import com.example.project.post.entity.Post;
import com.example.project.post.repository.PostRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 좋아요 생성
    public void like(Long currentUserId, Long postId) {

        // 이미 좋아요를 누른 경우인지 확인
        if (likeRepository.existsByUser_IdAndPost_Id(currentUserId, postId)) {
            throw new CustomException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        // 유저 존재 확인
        User user = userRepository.findById(currentUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 게시물 존재 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Like like = new Like(user, post);
        likeRepository.save(like);
    }

    // 좋아요 삭제
    public void unlike(Long currentUserId, Long postId) {

        // 좋아요 존재 확인
        Like like = likeRepository.findByUser_IdAndPost_Id(currentUserId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        likeRepository.delete(like);
    }
}

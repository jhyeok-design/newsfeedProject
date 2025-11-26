package com.example.project.comment.service;

import com.example.project.comment.model.response.CreateCommentResponse;
import com.example.project.comment.model.response.GetCommentResponse;
import com.example.project.comment.repository.CommentRepository;
import com.example.project.common.entity.Comment;
import com.example.project.common.entity.Post;
import com.example.project.common.entity.User;
import com.example.project.common.exception.CustomException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.post.repository.PostRepository;
import com.example.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CreateCommentResponse comment(Long currentUserId, Long postId, String comment) {

        // 유저 존재 확인
        User user = userRepository.findById(currentUserId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 게시물 존재 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Comment comments = new Comment(comment, user, post);
        Comment savedComments = commentRepository.save(comments);
        Long commentCount = commentRepository.countByPostId(postId);
        post.updateCommentCount(commentCount);
        return CreateCommentResponse.from(savedComments);
    }

    public Page<GetCommentResponse> findComments(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        Page<Comment> comments = commentRepository.findByPost(post, pageable);

        return comments.map(GetCommentResponse::from);

    }
}

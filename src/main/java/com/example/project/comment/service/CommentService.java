package com.example.project.comment.service;

import com.example.project.comment.model.response.CreateCommentResponse;
import com.example.project.comment.model.response.GetCommentResponse;
import com.example.project.comment.model.response.UpdateCommentResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    public CreateCommentResponse comment(Long currentUserId, Long postId, String comment) {
        User user = findUserOrException(currentUserId);
        Post post = findPostOrException(postId);

        Comment comments = new Comment(comment, user, post);
        Comment savedComments = commentRepository.save(comments);
        Long commentCount = commentRepository.countByPostId(postId);
        post.updateCommentCount(commentCount);
        return CreateCommentResponse.from(savedComments);
    }

    // 게시글 댓글 조회
    @Transactional(readOnly = true)
    public Page<GetCommentResponse> findComments(Long postId, Pageable pageable) {
        Post post = findPostOrException(postId);

        Page<Comment> comments = commentRepository.findByPost(post, pageable);

        return comments.map(GetCommentResponse::from);

    }

    // 게시글 댓글 수정
    public UpdateCommentResponse update(Long currentUserId, Long commentId, String comment) {
        Comment comments = findCommentOrException(commentId);
        commentByUserOrException(comments, currentUserId);

        comments.update(comment);
        commentRepository.flush();

        return UpdateCommentResponse.from(comments);
    }

    // 게시글 댓글 삭제
    public void delete(Long currentUserId, Long commentId) {
        Comment comments = findCommentOrException(commentId);
        commentByUserOrException(comments, currentUserId);

        commentRepository.delete(comments);
        Post post = comments.getPost();

        Long commentCount = commentRepository.countByPostId(comments.getPost().getId());
        post.updateCommentCount(commentCount);
    }

    private User findUserOrException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
    }

    private Post findPostOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }

    private Comment findCommentOrException(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    private void commentByUserOrException(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_AUTHOR_MISMATCH);
        }
    }

}

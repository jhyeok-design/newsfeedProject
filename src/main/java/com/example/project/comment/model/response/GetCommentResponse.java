package com.example.project.comment.model.response;

import com.example.project.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetCommentResponse {

    private final Long id;
    private final String comment;
    private final Long postId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static GetCommentResponse from(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getPost().getId(),
                comment.getUser().getNickname(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}

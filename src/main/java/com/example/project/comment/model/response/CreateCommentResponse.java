package com.example.project.comment.model.response;

import com.example.project.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {

    private final Long id;
    private final String comment;
    private final Long postId;
    private final String nickname;
    private final LocalDateTime createdAt;

    public static CreateCommentResponse from(Comment comment){
        return new CreateCommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getPost().getId(),
                comment.getUser().getNickname(),
                comment.getCreatedAt()
        );
    }
}

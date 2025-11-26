package com.example.project.comment.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateCommentResponse {

    private final Long id;
    private final String comment;
    private final Long postId;
    private final String nickname;
    private final LocalDateTime modifiedAt;
}

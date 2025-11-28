package com.example.project.comment.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글을 작성해주세요")
    private String comment;
}

package com.example.project.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 20, message = "제목은 20자를 넘을 수 없습니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = 500, message = "내용은 500자를 넘을 수 없습니다.")
    private String content;
}

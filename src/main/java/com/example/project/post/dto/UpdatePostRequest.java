package com.example.project.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePostRequest {
    @NotBlank(message = "제목은 필수로 입력해야합니다.")
    @Size(max = 20, message = "제목은 20자 이내로 작성해야합니다")
    String title;
    @NotBlank(message = "내용은 필수로 입력해야합니다.")
    @Size(max = 500, message = "제목은 500자 이내로 작성해야합니다")
    String content;
}

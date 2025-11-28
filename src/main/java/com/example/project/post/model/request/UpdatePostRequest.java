package com.example.project.post.model.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequest {
    @Size(max = 20, message = "제목은 20자 이내로 작성해야합니다")
    String title;
    @Size(max = 500, message = "제목은 500자 이내로 작성해야합니다")
    String content;
}

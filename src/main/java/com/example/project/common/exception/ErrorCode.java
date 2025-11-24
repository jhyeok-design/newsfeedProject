package com.example.project.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 유저"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "존재하지 않는 게시글"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "일치하지 않는 비밀번호"),
    SAME_PASSWORD(HttpStatus.CONFLICT, "CONFLICT", "동일한 비밀번호");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

package com.example.project.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 유저"),
    USER_DELETED(HttpStatus.NOT_FOUND, "USER_DELETED", "삭제된 유저입니다"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "존재하지 않는 게시글"),
    FOLLOWING_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOWING_USER_NOT_FOUND", "팔로우할 대상이 존재하지않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "일치하지 않는 비밀번호"),
    SAME_PASSWORD(HttpStatus.CONFLICT, "CONFLICT", "동일한 비밀번호"),
    EMPTY_POST_UPDATE(HttpStatus.BAD_REQUEST, "NO_ARGUMENT_INPUT", "입력된 값이 없습니다"),
    FOLLOW_ALREADY_EXISTS(HttpStatus.CONFLICT, "FOLLOW_ALREADY_EXISTS", "이미 팔로우한 사용자입니다."),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "SELF_FOLLOW_NOT_ALLOWED", "본인을 팔로우할 수 없습니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOW_NOT_FOUND", "팔로우 관계가 존재하지 않습니다."),
    NOT_RESOURCE_OWNER(HttpStatus.FORBIDDEN, "FORBIDDEN", "작성자만 가능한 작업입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

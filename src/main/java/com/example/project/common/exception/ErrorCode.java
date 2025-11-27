package com.example.project.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 유저입니다."),
    USER_DELETED(HttpStatus.NOT_FOUND, "USER_DELETED", "삭제된 유저입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "존재하지 않는 게시물입니다."),
    FOLLOWING_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOWING_USER_NOT_FOUND", "팔로우할 대상이 존재하지 않습니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOW_NOT_FOUND", "팔로우 관계가 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"COMMENT_NOT_FOUND","변경할 댓글이 없습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE_NOT_FOUND", "좋아요 기록이 없습니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "DUPLICATE_NICKNAME", "이미 존재하는 닉네임입니다."),
    SAME_PASSWORD(HttpStatus.CONFLICT, "SAME_PASSWORD", "새 비밀번호가 기존 비밀번호와 동일합니다."),
    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "LIKE_ALREADY_EXISTS", "이미 좋아요를 눌렀습니다."),
    FOLLOW_ALREADY_EXISTS(HttpStatus.CONFLICT, "FOLLOW_ALREADY_EXISTS", "이미 팔로우한 사용자입니다."),

    NOTHING_TO_UPDATE(HttpStatus.BAD_REQUEST, "NOTHING_TO_UPDATE", "수정할 값이 없습니다."),
    INVALID_NICKNAME_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_NICKNAME_FORMAT", "닉네임은 영문 소문자 시작, 숫자와 '_'만 가능하며 3~20자여야 합니다."),
    INVALID_PASSWORD_INPUT(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD_INPUT", "기존 비밀번호와 새 비밀번호 모두 입력해주세요."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD_FORMAT", "비밀번호는 공백 없이 8자 이상이며 대문자, 소문자, 숫자, 특수문자를 최소 1개씩 포함해야 합니다."),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "SELF_FOLLOW_NOT_ALLOWED", "본인을 팔로우할 수 없습니다."),

    NOT_RESOURCE_OWNER(HttpStatus.FORBIDDEN, "NOT_RESOURCE_OWNER", "작성자만 가능한 작업입니다."),
    COMMENT_AUTHOR_MISMATCH(HttpStatus.FORBIDDEN, "COMMENT_AUTHOR_MISMATCH", "해당 댓글의 작성자가 아닙니다."),

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

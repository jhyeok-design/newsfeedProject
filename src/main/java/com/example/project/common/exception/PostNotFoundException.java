package com.example.project.common.exception;

/**
 * 게시물이 존재하지 않을 경우 예외 처리
 */
public class PostNotFoundException extends CustomException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}

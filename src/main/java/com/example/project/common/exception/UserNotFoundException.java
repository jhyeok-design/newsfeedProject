package com.example.project.common.exception;

/**
 * 유저가 존재하지 않을 경우 예외 처리
 */
public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}

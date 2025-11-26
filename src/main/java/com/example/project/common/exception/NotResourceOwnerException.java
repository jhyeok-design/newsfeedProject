package com.example.project.common.exception;

/**
 * 작성자가 아닌 사용자가 수정/삭제를 시도한 경우 예외 처리
 */
public class NotResourceOwnerException extends CustomException {
    public NotResourceOwnerException() {
        super(ErrorCode.NOT_RESOURCE_OWNER);
    }
}

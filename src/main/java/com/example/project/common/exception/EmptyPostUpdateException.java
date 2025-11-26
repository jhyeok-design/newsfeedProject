package com.example.project.common.exception;

public class EmptyPostUpdateException extends CustomException {
    public EmptyPostUpdateException() {
        super(ErrorCode.EMPTY_POST_UPDATE);
    }
}
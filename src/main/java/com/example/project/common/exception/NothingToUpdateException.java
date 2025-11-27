package com.example.project.common.exception;

public class NothingToUpdateException extends CustomException {
    public NothingToUpdateException() {
        super(ErrorCode.NOTHING_TO_UPDATE);
    }
}
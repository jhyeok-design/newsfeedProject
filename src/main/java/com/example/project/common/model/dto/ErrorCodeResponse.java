package com.example.project.common.model.dto;

import com.example.project.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@JsonPropertyOrder({"status", "code", "message"})
public class ErrorCodeResponse {

    private final int status;
    private final String code;
    private final String message;

    public ErrorCodeResponse(HttpStatus httpStatus, String code, String message) {
        this.status = httpStatus.value();
        this.code = code;
        this.message = message;
    }

    public static ErrorCodeResponse from(ErrorCode errorCode) {
        return new ErrorCodeResponse(
                errorCode.getStatus(),
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}


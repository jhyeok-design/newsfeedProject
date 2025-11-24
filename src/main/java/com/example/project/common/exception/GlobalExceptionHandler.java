package com.example.project.common.exception;

import com.example.project.common.model.dto.ErrorCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorCodeResponse> handleCustomException(CustomException c) {
        log.error("CustomException 발생", c);

        ErrorCode errorCode = c.getErrorCode();

        ErrorCodeResponse response = ErrorCodeResponse.from(errorCode);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }
}

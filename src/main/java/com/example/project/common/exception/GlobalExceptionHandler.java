package com.example.project.common.exception;

import com.example.project.common.model.dto.ErrorCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // 유효성 검사 실패 시 처리 (@Valid 관련 예외)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCodeResponse> handleResponseStatusException(MethodArgumentNotValidException m) {
        log.error("유효성 검사 실패(MethodArgumentNotValidException) 발생", m);

        String message = "Validation failed";

        FieldError fieldError = m.getBindingResult().getFieldError();
        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
        }

        HttpStatus status = HttpStatus.valueOf(m.getStatusCode().value());
        String codeName = status.name();

        return ResponseEntity
                .status(status)
                .body(new ErrorCodeResponse(
                        status,
                        codeName,
                        message
                ));
    }


}

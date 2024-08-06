package com.example.lab.handler;

import com.example.lab.exception.LanguageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class WebAppExceptionHandler {

    @ExceptionHandler(value = ResourceAccessException.class)
    public ResponseEntity<ErrorResponseBody> resourceAccessHandler(ResourceAccessException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = LanguageNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> languageNotFoundHandler(LanguageNotFoundException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponseBody.builder()
                        .message(ex.getMessage())
                        .build());
    }
}
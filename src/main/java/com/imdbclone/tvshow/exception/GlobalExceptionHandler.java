package com.imdbclone.tvshow.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", exception.getHttpStatus().value());
        response.put("error", exception.getHttpStatus().getReasonPhrase());
        response.put("message", exception.getMessage());
        response.put("solution", exception.getSolution());

        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}

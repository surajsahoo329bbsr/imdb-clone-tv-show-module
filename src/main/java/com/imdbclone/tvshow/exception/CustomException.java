package com.imdbclone.tvshow.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;
    private String solution;

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomException(HttpStatus httpStatus, String message, String solution) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.solution = solution;
    }

}

package com.imdbclone.tvshow.exception;

import com.imdbclone.tvshow.entity.AppLog;
import com.imdbclone.tvshow.model.ActionStatus;
import com.imdbclone.tvshow.service.api.IAppLogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import util.JWTUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final IAppLogService appLogService;
    private final JWTUtils jwtUtils;
    private final HttpServletRequest httpServletRequest;

    public GlobalExceptionHandler(IAppLogService appLogService, JWTUtils jwtUtils, HttpServletRequest httpServletRequest) {
        this.appLogService = appLogService;
        this.jwtUtils = jwtUtils;
        this.httpServletRequest = httpServletRequest;
    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        String serviceName = (String) httpServletRequest.getAttribute("serviceName");
        Long entityId = (Long) httpServletRequest.getAttribute("entityId");
        String serviceMethod = (String) httpServletRequest.getAttribute("serviceMethod");

        AppLog appLog = AppLog.builder()
                .entityId(entityId) // Set properly if possible
                .serviceName(serviceName)
                .serviceMethod(serviceMethod)
                .actionStatus(ActionStatus.FAILURE)
                .httpStatusCode(status.value())
                .exceptionMessage(exception.getMessage())
                .errorMessage(Arrays.toString(exception.getStackTrace()))
                .createdAt(LocalDateTime.now())
                .createdBy(jwtUtils.getAdminIdFromJwt()) // Extract from JWT if applicable
                .ipAddress(httpServletRequest.getRemoteAddr())
                .build();

        appLogService.logAction(appLog);

        return new ResponseEntity<>(exception.getMessage(), status);
    }
}

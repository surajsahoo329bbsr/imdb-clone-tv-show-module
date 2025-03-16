package com.imdbclone.tvshow.interceptor;

import com.imdbclone.tvshow.entity.AppLog;
import com.imdbclone.tvshow.model.ActionStatus;
import com.imdbclone.tvshow.model.ActionType;
import com.imdbclone.tvshow.service.api.IAppLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import util.JWTUtils;

import java.time.LocalDateTime;
import java.util.Arrays;


@Aspect
@Component
public class LoggingInterceptor {

    private final IAppLogService appLogService;
    private final JWTUtils jwtUtils;

    public LoggingInterceptor(IAppLogService appLogService, JWTUtils jwtUtils) {
        this.appLogService = appLogService;
        this.jwtUtils = jwtUtils;
    }

    private String getClientIp() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }


    @Around("@annotation(org.springframework.transaction.annotation.Transactional) && execution(* com.imdbclone.tvshow.service.implementation.TVShowServiceImpl..*(..))")
    public Object logAfterTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        ActionStatus actionStatus = ActionStatus.UNKNOWN;
        String exceptionMessage = null;
        String errorMessage = null;
        ActionType actionType = ActionType.UNKNOWN;
        String methodName = joinPoint.getSignature().getName();
        String entityType = joinPoint.getTarget().getClass().getSimpleName();
        Long entityId = 1L;
        Long createdBy = 0L;
        String ipAddress = getClientIp();
        HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            createdBy = jwtUtils.getAdminIdFromJwt();
            result = joinPoint.proceed();

            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof Long) {
                entityId = (Long) args[0];
            }

            if (result instanceof ResponseEntity<?> responseEntity) {
                httpStatusCode = responseEntity.getStatusCode();
            }

            if (methodName.toLowerCase().contains("add") || methodName.toLowerCase().contains("upload")) {
                actionType = ActionType.CREATE;
            } else if (methodName.toLowerCase().contains("get")) {
                actionType = ActionType.READ;
            } else if (methodName.toLowerCase().contains("update")) {
                actionType = ActionType.UPDATE;
            } else if (methodName.toLowerCase().contains("delete")) {
                actionType = ActionType.DELETE;
            }

            actionStatus = ActionStatus.SUCCESSFUL;
        } catch (Exception exception) {
            actionStatus = ActionStatus.FAILURE;
            actionType = ActionType.EXCEPTION;
            exceptionMessage = exception.getMessage();
            errorMessage = Arrays.toString(exception.getStackTrace());

            throw exception;
        } finally {
            AppLog appLog = AppLog.builder()
                    .entityType(entityType)
                    .entityId(entityId)
                    .actionType(actionType)
                    .actionStatus(actionStatus)
                    .HttpStatusCode(httpStatusCode.value())
                    .exceptionMessage(exceptionMessage)
                    .errorMessage(errorMessage)
                    .createdAt(LocalDateTime.now())
                    .createdBy(createdBy) // Set the actual logged-in user ID
                    .ipAddress(ipAddress)
                    .build();

            appLogService.logAction(appLog);
        }

        return result;
    }


}


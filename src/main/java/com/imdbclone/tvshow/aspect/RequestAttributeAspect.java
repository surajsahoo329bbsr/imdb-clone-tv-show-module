package com.imdbclone.tvshow.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class RequestAttributeAspect {

    private final HttpServletRequest httpServletRequest;

    public RequestAttributeAspect(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Around("@annotation(com.imdbclone.tvshow.annotation.SetRequestAttributes)")
    public Object setRequestAttributes(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String serviceName = joinPoint.getTarget().getClass().getSimpleName().replaceAll("$$.*", "");
            httpServletRequest.setAttribute("serviceName", serviceName);

            String methodName = joinPoint.getSignature().getName();
            httpServletRequest.setAttribute("serviceMethod", methodName);

            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                Parameter parameter = joinPoint.getSignature().getDeclaringType().getDeclaredMethods()[0]
                        .getParameters()[i];

                if (parameter.isAnnotationPresent(PathVariable.class)) {
                    httpServletRequest.setAttribute("entityId", args[i]);
                    break;
                } else if (parameter.isAnnotationPresent(RequestBody.class)) {

                    Object requestBody = args[i];
                    if (requestBody != null) {
                        Field[] fields = requestBody.getClass().getDeclaredFields();
                        if (fields.length > 0) {
                            fields[0].setAccessible(true);
                            Object entityIdValue = fields[0].get(requestBody);
                            httpServletRequest.setAttribute("entityId", entityIdValue);
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }

        return joinPoint.proceed();
    }

}

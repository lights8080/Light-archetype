package com.light.config.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.light.config.logger.LoggerConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class ControllerLogAspect {
    private final static Logger logger = LoggerFactory.getLogger("ControllerAspectLog");

    @Autowired
    private LoggerConfig loggerConfig;

    @Pointcut("execution(* com.light.controller.*.*(..))")
    public void printLog() {
    }

    @Around("printLog()")
    private Object doAround(ProceedingJoinPoint pjp) {
        try {
            long start = System.nanoTime();
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
            String requestData = null;
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
                requestData = JSONArray.toJSONString(pjp.getArgs());
            } else if ("GET".equalsIgnoreCase(request.getMethod())) {
                requestData = request.getQueryString();
            }
            if (logger.isInfoEnabled() && loggerConfig.getController().isEnabled()) {
                if (!loggerConfig.getController().getSkipMethodSigns().contains(pjp.getSignature().toShortString())) {
                    logger.info("LIGHT-REQ-[{}] {}", pjp.getSignature().toShortString(), (requestData != null ? requestData : ""));
                }
            }
            Object result = pjp.proceed();
            long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            String responseData = null;
            if (result != null) {
                responseData = JSON.toJSONString(result);
            }
            if (logger.isInfoEnabled() && loggerConfig.getController().isEnabled()) {
                if (!loggerConfig.getController().getSkipMethodSigns().contains(pjp.getSignature().toShortString())) {
                    if (loggerConfig.getController().getIgnoreResponseDataMethodSigns().contains(pjp.getSignature().toShortString())) {
                        logger.info("LIGHT-RESP-[{}] IGNORE_DATA size:{} spend:{}ms", pjp.getSignature().toShortString(), (responseData != null ? responseData.length() : 0), elapsedTime);
                    } else {
                        logger.info("LIGHT-RESP-[{}] {} size:{} spend:{}ms", pjp.getSignature().toShortString(), (responseData != null ? responseData : ""), (responseData != null ? responseData.length() : 0), elapsedTime);
                    }
                }
            }
            return result;
        } catch (Throwable throwable) {
            if (logger.isErrorEnabled() && loggerConfig.getController().isEnabled()) {
                logger.error("LIGHT-RESP-[{}] {}: {}", pjp.getSignature().toShortString(), throwable.getClass().getSimpleName(), throwable.getMessage());
            }
            return throwable;
        }
    }
}


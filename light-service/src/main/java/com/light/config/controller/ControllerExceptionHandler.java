package com.light.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Map handleException(Exception e) {
        Map map = new HashMap();
        map.put("code", 500);
        map.put("msg", "系统错误");
        log.error("Exception: ", e);
        return map;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map map = new HashMap();
        map.put("code", 501);
        map.put("msg", "方法不支持");
        log.error("HttpRequestMethodNotSupportedException: ", e);
        return map;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map map = new HashMap();
        map.put("code", 502);
        map.put("msg", "参数错误");
        log.error("MethodArgumentNotValidException: ", e);
        return map;
    }
}

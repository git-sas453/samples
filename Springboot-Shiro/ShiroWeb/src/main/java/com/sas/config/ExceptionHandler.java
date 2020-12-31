package com.sas.config;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthorizationException.class)
    public Object shiroHandler() {
        return "未授权";
    }

}

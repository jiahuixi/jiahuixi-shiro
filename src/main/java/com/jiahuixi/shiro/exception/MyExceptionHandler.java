package com.jiahuixi.shiro.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException() {
        return "/403";
    }
}

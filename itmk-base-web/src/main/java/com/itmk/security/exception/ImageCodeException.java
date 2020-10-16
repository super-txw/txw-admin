package com.itmk.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常处理类
 */
public class ImageCodeException extends AuthenticationException {
    public ImageCodeException(String msg) {
        super(msg);
    }
}

package com.itmk.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * token验证失败异常
 */
public class TokenException extends AuthenticationException {
    public TokenException(String msg) {
        super(msg);
    }
}

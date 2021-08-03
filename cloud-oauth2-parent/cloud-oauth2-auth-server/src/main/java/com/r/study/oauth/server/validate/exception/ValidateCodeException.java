package com.r.study.oauth.server.validate.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 * date 2021-06-23 11:12
 *
 * @author HeYiHui
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }
}

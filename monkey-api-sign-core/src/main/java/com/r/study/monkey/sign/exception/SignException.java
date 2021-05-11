package com.r.study.monkey.sign.exception;

/**
 * date 2020-12-17 19:43
 *
 * @author HeYiHui
 **/
public class SignException extends RuntimeException {
    public SignException() {
    }

    public SignException(String message) {
        super(message);
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(Throwable cause) {
        super(cause);
    }

    public SignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

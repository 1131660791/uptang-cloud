package com.uptang.cloud.core.exception;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class AuthenticationException extends BusinessException {
    private static final long serialVersionUID = 7132125363768518519L;

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Integer code, String message) {
        super(code, message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

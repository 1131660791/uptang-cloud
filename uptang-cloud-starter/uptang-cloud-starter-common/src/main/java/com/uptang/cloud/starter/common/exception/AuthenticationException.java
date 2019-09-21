package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class AuthenticationException extends BusinessException {
    private static final long serialVersionUID = 7132125363768518519L;

    public AuthenticationException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public AuthenticationException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

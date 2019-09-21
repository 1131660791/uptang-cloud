package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class NotImplementException extends BusinessException {
    private static final long serialVersionUID = 7490028578392179683L;

    public NotImplementException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public NotImplementException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public NotImplementException(String msg) {
        super(msg);
    }

    public NotImplementException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

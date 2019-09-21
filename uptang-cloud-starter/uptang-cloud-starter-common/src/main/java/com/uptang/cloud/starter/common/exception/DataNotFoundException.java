package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataNotFoundException extends BusinessException {
    private static final long serialVersionUID = -7465441277582472456L;

    public DataNotFoundException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public DataNotFoundException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public DataNotFoundException(String msg) {
        super(msg);
    }

    public DataNotFoundException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

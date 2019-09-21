package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataAccessException extends BusinessException {
    private static final long serialVersionUID = 5312257298642181576L;

    public DataAccessException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public DataAccessException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public DataAccessException(String msg) {
        super(msg);
    }

    public DataAccessException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

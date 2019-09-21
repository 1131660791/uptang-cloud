package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataExistedException extends BusinessException {
    private static final long serialVersionUID = -7465441277582472456L;

    public DataExistedException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public DataExistedException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public DataExistedException(String msg) {
        super(msg);
    }

    public DataExistedException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

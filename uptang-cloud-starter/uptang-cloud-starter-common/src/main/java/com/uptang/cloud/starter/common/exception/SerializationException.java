package com.uptang.cloud.starter.common.exception;

import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class SerializationException extends BusinessException {
    private static final long serialVersionUID = -5263231856149520081L;

    public SerializationException(ResponseCodeEnum responseCode) {
        super(responseCode);
    }

    public SerializationException(ResponseCodeEnum responseCode, Object... objects) {
        super(responseCode, objects);
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable ex) {
        super(msg, ex);
    }
}

package com.uptang.cloud.core.exception;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class SerializationException extends BusinessException {
    private static final long serialVersionUID = -5263231856149520081L;

    public SerializationException(Throwable cause) {
        super(cause);
    }

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(Integer code, String message) {
        super(code, message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

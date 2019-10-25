package com.uptang.cloud.core.exception;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataNotFoundException extends BusinessException {
    private static final long serialVersionUID = -7465441277582472456L;

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Integer code, String message) {
        super(code, message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

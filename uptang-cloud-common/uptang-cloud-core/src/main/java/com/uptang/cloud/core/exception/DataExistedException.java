package com.uptang.cloud.core.exception;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataExistedException extends BusinessException {
    private static final long serialVersionUID = -7465441277582472456L;

    public DataExistedException(Throwable cause) {
        super(cause);
    }

    public DataExistedException(String message) {
        super(message);
    }

    public DataExistedException(Integer code, String message) {
        super(code, message);
    }

    public DataExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataExistedException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

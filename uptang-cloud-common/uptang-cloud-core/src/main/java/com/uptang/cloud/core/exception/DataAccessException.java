package com.uptang.cloud.core.exception;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class DataAccessException extends BusinessException {
    private static final long serialVersionUID = 5312257298642181576L;

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Integer code, String message) {
        super(code, message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

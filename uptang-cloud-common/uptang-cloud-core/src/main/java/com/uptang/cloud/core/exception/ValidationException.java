package com.uptang.cloud.core.exception;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-11
 */
public class ValidationException extends BusinessException {
    private static final long serialVersionUID = -2973823306153598618L;

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Integer code, String message) {
        super(code, message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

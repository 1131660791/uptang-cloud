package com.uptang.cloud.core.exception;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class NotImplementException extends BusinessException {
    private static final long serialVersionUID = 7490028578392179683L;

    public NotImplementException(Throwable cause) {
        super(cause);
    }

    public NotImplementException(String message) {
        super(message);
    }

    public NotImplementException(Integer code, String message) {
        super(code, message);
    }

    public NotImplementException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}

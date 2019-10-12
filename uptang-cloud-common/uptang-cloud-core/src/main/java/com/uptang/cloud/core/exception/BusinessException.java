package com.uptang.cloud.core.exception;

import lombok.Getter;

/**
 * 业务异常
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 6912363249855903393L;
    private Integer code;

    /**
     * 创建异常
     *
     * @param cause 异常根源
     */
    public BusinessException(Throwable cause) {
        this(null, null, cause);
    }

    /**
     * 创建异常
     *
     * @param message 异常信息
     */
    public BusinessException(String message) {
        this(null, message, null);
    }

    /**
     * 创建异常
     *
     * @param code    异常状态码
     * @param message 提示信息
     */
    public BusinessException(Integer code, String message) {
        this(code, message, null);
    }

    /**
     * 创建异常
     *
     * @param message 异常信息
     * @param cause   异常根源
     */
    public BusinessException(String message, Throwable cause) {
        this(null, message, cause);
    }


    /**
     * 创建异常
     *
     * @param code    异常代码
     * @param message 异常消息
     * @param cause   异常根源
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}

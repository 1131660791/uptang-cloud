package com.uptang.cloud.score.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 21:11
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExcelException extends RuntimeException {

    public ExcelException(String msg) {
        this(msg, false);
    }

    public ExcelException(Throwable msg) {
        super(msg);
    }

    public ExcelException(String msg, boolean recordStackTrace) {
        super((msg == null || "".equals(msg) ? "解析Excel失败" : msg), null, false, recordStackTrace);
    }
}

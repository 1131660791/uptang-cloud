package com.uptang.cloud.score.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 21:11
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@ResponseStatus
public class ExcelExceptions extends RuntimeException {

    public ExcelExceptions() {
        this(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public ExcelExceptions(String msg) {
        this(msg, false);
    }

    public ExcelExceptions(Throwable msg) {
        super(msg);
    }

    public ExcelExceptions(String msg, boolean recordStackTrace) {
        super((msg == null || "".equals(msg) ? "解析Excel失败" : msg), null, false, recordStackTrace);
    }

    public ExcelExceptions(String msg, Throwable cause) {
        super(msg, cause, false, true);
    }
}

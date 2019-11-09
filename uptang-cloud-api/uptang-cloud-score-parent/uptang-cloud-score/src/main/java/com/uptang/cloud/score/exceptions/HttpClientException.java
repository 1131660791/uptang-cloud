package com.uptang.cloud.score.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 13:00
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@ResponseStatus
public class HttpClientException extends RuntimeException {

    public HttpClientException() {
        this(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public HttpClientException(String msg) {
        this(msg, false);
    }

    public HttpClientException(Throwable msg) {
        super(msg);
    }

    public HttpClientException(String msg, boolean recordStackTrace) {
        super((msg == null || "".equals(msg) ? "解析Excel失败" : msg), null, false, recordStackTrace);
    }

    public HttpClientException(String msg, Throwable cause) {
        super(msg, cause, false, true);
    }
}

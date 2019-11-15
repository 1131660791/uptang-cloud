package com.uptang.cloud.score.httpclient;

import java.io.Serializable;

/**
 * @Author : Lee
 * @CreateTime : 2018/11/25 8:48 PM
 * @Mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
 * @Summary : FIXME
 */
public class HttpResponseEvent implements Serializable {

    private int code;

    private String message;

    private String payload;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{ \"code\":\"" + code + "\",  \"message\":\"" + message + "\",  \"payload\":\"" + payload + "\"}}";
    }
}
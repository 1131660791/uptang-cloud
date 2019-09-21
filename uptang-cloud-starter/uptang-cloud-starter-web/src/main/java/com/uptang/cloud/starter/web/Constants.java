package com.uptang.cloud.starter.web;

import org.springframework.http.MediaType;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public interface Constants extends com.uptang.cloud.starter.common.Constants {
    /**
     * API 返回JSON格式
     */
    String MEDIA_JSON_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;


    /**
     * 内部接口采用二进制序列化
     */
    String MEDIA_INNER_TYPE = "application/x-protostuff;charset=UTF-8";

    /**
     * API 返回纯文本格式
     */
    String MEDIA_TEXT_TYPE = MediaType.TEXT_PLAIN_VALUE;
}

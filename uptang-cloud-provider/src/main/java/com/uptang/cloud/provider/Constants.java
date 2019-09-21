package com.uptang.cloud.provider;

import org.springframework.http.MediaType;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public interface Constants {
    String PRODUCE_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
    String CONSUME_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    /**
     * 采用二进制的方式进行内部数据传输
     */
    String PRODUCE_BINARY_TYPE = "application/x-protostuff;charset=UTF-8";
    String CONSUME_BINARY_TYPE = "application/x-protostuff";

}

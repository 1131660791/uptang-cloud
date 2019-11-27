package com.uptang.cloud.base.feign;

import org.springframework.http.MediaType;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-14
 */
public interface Constants {
    String SERVICE_NAME = "uptang-cloud-base";
    String CONTEXT_PATH = "/common";

    String PRODUCE_MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
    String CONSUME_MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;
}

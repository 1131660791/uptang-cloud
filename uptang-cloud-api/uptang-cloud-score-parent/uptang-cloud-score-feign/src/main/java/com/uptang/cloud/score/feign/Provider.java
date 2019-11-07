package com.uptang.cloud.score.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 19:35
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@FeignClient(name = "${spring.application.name:" + Provider.SERVICE_NAME + "}")
public interface Provider {
    String SERVICE_NAME = "uptang-cloud-score";
}

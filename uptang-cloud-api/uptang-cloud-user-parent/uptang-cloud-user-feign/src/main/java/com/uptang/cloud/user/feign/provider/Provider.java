package com.uptang.cloud.user.feign.provider;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 服务提供者 基类
 * @author cht
 * @date 2019-11-19
 */
@FeignClient(name = "${spring.application.name:" + Provider.SERVICE_NAME + "}")
public interface Provider {
    String SERVICE_NAME = "uptang-cloud-user";
}

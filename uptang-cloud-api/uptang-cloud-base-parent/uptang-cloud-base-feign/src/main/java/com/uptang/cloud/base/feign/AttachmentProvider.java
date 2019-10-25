package com.uptang.cloud.base.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-11
 */
@FeignClient(name = AttachmentProvider.SERVICE_NAME)
public interface AttachmentProvider {
    String SERVICE_NAME = "uptang-cloud-base";
}

package com.uptang.cloud.score.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@FeignClient(name = ScoreProvider.SERVICE_NAME)
public interface ScoreProvider {
    String SERVICE_NAME = "uptang-cloud-score";
}

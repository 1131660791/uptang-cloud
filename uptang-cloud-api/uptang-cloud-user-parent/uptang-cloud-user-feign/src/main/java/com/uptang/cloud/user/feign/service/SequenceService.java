package com.uptang.cloud.user.feign.service;

import com.uptang.cloud.user.feign.fallbak.SequenceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 全局序列服务
 * @author cht
 * @date 2019-11-19
 */
@FeignClient(value = SequenceService.SERVICE_NAME, fallbackFactory = SequenceFallbackFactory.class)
public interface SequenceService {
    String SERVICE_NAME = "uptang-cloud-sequence";
    String API_PREFIX = "/v1/inner/sequence";

    /**
     * 获取单个序列
     * @return 单个序列
     */
    @RequestMapping(value = API_PREFIX, method= RequestMethod.GET)
    long getSequence();

    /**
     * 获取多个序列
     * @param count 获取序列数量
     * @return 多个序列
     */
    @RequestMapping(path = API_PREFIX + "/{count}", method= RequestMethod.GET)
    long[] getSequences(@PathVariable(name = "count") Integer count);

}

package com.uptang.cloud.provider.sequence;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@FeignClient(name = SequenceProvider.SERVICE_NAME)
public interface SequenceProvider {
    String SERVICE_NAME = "uptang-cloud-sequence";
    String API_PREFIX = "/v1/inner/sequence";

    /**
     * 获取一个序列ID
     *
     * @return 全局唯一的序列ID
     */
    @GetMapping(path = API_PREFIX)
    long getSequence();

    /**
     * 获取一组序列ID
     *
     * @param count 序列个数
     * @return 全局唯一的序列ID
     */
    @GetMapping(path = API_PREFIX + "/{count}")
    long[] getSequences(@PathVariable(name = "count") Integer count);
}

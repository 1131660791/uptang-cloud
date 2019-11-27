package com.uptang.cloud.user.feign.fallbak;

import com.uptang.cloud.user.feign.service.SequenceService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * 请求全局序列服务熔断工厂
 * @author cht
 * @date 2019-11-19
 */
@Slf4j
@Component
public class SequenceFallbackFactory implements FallbackFactory<SequenceService> {

    @Override
    public SequenceService create(Throwable throwable) {
        return new SequenceService() {
            @Override
            public long getSequence() {
                log.error("getSequence fallback reason was:", throwable);
                return 0;
            }

            @Override
            public long[] getSequences(Integer count) {
                log.error("getSequences fallback reason was:", throwable);
                return new long[0];
            }
        };
    }

}

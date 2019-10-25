package com.uptang.cloud.sequence.controller;

import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.sequence.feign.SequenceProvider;
import com.uptang.cloud.sequence.util.GlobalSequenceGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@RestController
@Api(value = "SequenceController", tags = {"全局序列"})
public class SequenceController implements SequenceProvider {
    /**
     * 默认生成序号数量
     */
    private static final int DEFAULT_COUNT = 10;

    @Override
    public long getSequence() {
        return GlobalSequenceGenerator.getInstance().generateSequenceId();
    }

    @Override
    public long[] getSequences(Integer count) {
        count = NumberUtils.isPositive(count) ? count : DEFAULT_COUNT;
        long[] ids = GlobalSequenceGenerator.getInstance().generateSequenceIds(count);
        log.info("生成的序号： {}", Arrays.toString(ids));
        return ids;
    }
}

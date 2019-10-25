package com.uptang.cloud.sequence.common;

import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public interface Constants extends com.uptang.cloud.core.Constants {

    /**
     * 时间戳差值的起点
     */
    long EPOCH_MILLI = LocalDate.of(2010, 1, 1)
            .atStartOfDay(ZoneOffset.ofHours(8)).toInstant()
            .toEpochMilli();
}

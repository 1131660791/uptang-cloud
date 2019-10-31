package com.uptang.cloud.task.util;

import com.uptang.cloud.core.util.StringUtils;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-14
 */
public class CacheKeys extends com.uptang.cloud.core.util.CacheKeys {
    /**
     * 考试项目提取
     */
    private static final String CACHE_KEY_EXTRACT_PREFIX = String.join(SEPARATOR, new String[]{"task", "exam", "extract"});

    /**
     * 考试项目处理
     */
    private static final String CACHE_KEY_PROCESS_PREFIX = String.join(SEPARATOR, new String[]{"task", "exam", "process"});

    /**
     * 通过Redis的集合(Set)来接收需要处理的考试代码
     * => task:exam:extract:queue
     *
     * @return Cache Key
     */
    public static String getExamExtractTaskKey() {
        return CacheKeys.getKey(CACHE_KEY_EXTRACT_PREFIX, "queue");
    }

    /**
     * 通过Redis来记录试卷代码发送到消息队列的进度
     * => task:exam:extract:progress
     *
     * @return Cache Key
     */
    public static String getExamExtractProgressKey() {
        return CacheKeys.getKey(CACHE_KEY_EXTRACT_PREFIX, "progress");
    }


    /**
     * 根据考试代码生成恢复点缓存Key
     * => task:exam:extract:crash_xty_20190617150608344
     *
     * @param examCode 考试代码
     * @return CacheKey
     */
    public static String getExamExtractCrashPointKey(String examCode) {
        return CacheKeys.getKey(CACHE_KEY_EXTRACT_PREFIX, "crash", StringUtils.trimToEmpty(examCode).trim());
    }

    /**
     * 根据考试代码生成处理进度缓存Key
     * => task:exam:process:progress
     *
     * @return Cache Key
     */
    public static String getExamProcessProgressKey() {
        return CacheKeys.getKey(CACHE_KEY_PROCESS_PREFIX, "progress");
    }
}

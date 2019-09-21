package com.uptang.cloud.behavior.util;

import lombok.extern.slf4j.Slf4j;


/**
 * 每个模块使用自己的 CacheKeys
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class CacheKeys extends com.uptang.cloud.starter.common.util.CacheKeys {

    /**
     * 根据用户ID生成缓存Key
     *
     * @param userId 用户ID
     * @return 缓存Key, eg: user:info:123
     */
    public static String getUserInfoKey(String userId) {
        return getKey(String.join(SEPARATOR, "user", "info"), userId);
    }
}

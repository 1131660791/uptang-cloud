package com.uptang.cloud.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 每个模块使用自己的 CacheKeys
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class CacheKeys {
    protected static final String SEPARATOR = ":";

    /**
     * 根据参数生成缓存Key
     *
     * @param prefix 前缀
     * @param keys   用作唯一标识缓存Key
     * @return 生成出来的缓存Key
     */
    public static String getKey(String prefix, String... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("缓存KEY不能为空！");
        }

        // 只有一个
        if (1 == keys.length && StringUtils.isNotBlank(keys[0])) {
            return (prefix.trim() + SEPARATOR + keys[0].trim()).toLowerCase();
        }

        // 将多个字符连接起来
        String keyStr = Arrays.stream(keys).map(StringUtils::trimToNull)
                .filter(Objects::nonNull).sorted().collect(Collectors.joining("_"));
        if (StringUtils.isBlank(keyStr)) {
            throw new IllegalArgumentException("缓存KEY不能为空！");
        }

        return (prefix.trim() + SEPARATOR + keyStr).toLowerCase();
    }


    /**
     * 根据参数生成缓存Key
     * CacheKeys.getKey("ord", 100) => ord:100
     *
     * @param prefix 前缀
     * @param keys   用作唯一标识缓存Key
     * @return 生成出来的缓存Key
     */
    public static String getKey(String prefix, Number... keys) {
        if (ArrayUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("缓存KEY不能为空！");
        }

        // 只有一个
        if (1 == keys.length && NumberUtils.isPositive(keys[0])) {
            return (prefix.trim() + SEPARATOR + keys[0]).toLowerCase();
        }

        // 将多个字符连接起来
        String keyStr = Arrays.stream(keys).filter(NumberUtils::isPositive).sorted()
                .map(String::valueOf).collect(Collectors.joining("_"));
        if (StringUtils.isBlank(keyStr)) {
            throw new IllegalArgumentException("缓存KEY不能为空！");
        }

        return (prefix.trim() + SEPARATOR + keyStr).toLowerCase();
    }
}

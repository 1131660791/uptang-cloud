package com.uptang.cloud.starter.data.redis;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.uptang.cloud.pojo.domain.cache.SerializerWrapper;
import com.uptang.cloud.starter.common.util.CollectionUtils;
import com.uptang.cloud.starter.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class RedisUtils {
    /**
     * Redis 连接
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据缓存Key前缀删除缓存中的数据
     *
     * @param pattern 缓存的前缀
     * @return 删除成功的数量
     */
    public Long deleteByPattern(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return 0L;
        }

        final String keyPattern = StringUtils.endsWith(pattern, "*") ? pattern : pattern + "*";
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.openPipeline();
            try {
                Set<byte[]> keys = connection.keys(keyPattern.getBytes());
                if (CollectionUtils.isEmpty(keys)) {
                    return 0L;
                }
                return connection.del(keys.toArray(new byte[keys.size()][]));
            } finally {
                connection.closePipeline();
            }
        });
    }

    /**
     * 从Redis中获取数据, 可能会出现数据格式不兼容
     *
     * @param key           缓存Key
     * @param expirySeconds 过期时间
     * @param supplier      通过其它方式产生数据
     * @param <V>           返回的数据类型
     * @return 缓存中的数据
     */
    public <V> V mutexGetAndSetIfNeeded(String key, Integer expirySeconds, Supplier<? extends V> supplier) {
        final AtomicInteger loopCount = new AtomicInteger(0), sleepTime = new AtomicInteger(50);
        final long maxTimeout = TimeUnit.SECONDS.toMillis(2);
        final int retryTimes = 6;
        V finalValue = null;

        // 防止 OutOfStack，限制循环次数
        while (Objects.isNull(finalValue) && loopCount.getAndIncrement() < retryTimes && sleepTime.get() < maxTimeout) {
            // 从缓存中查询数据
            Object serializedValue = redisTemplate.opsForValue().get(key);
            if (Objects.nonNull(serializedValue)) {
                if (serializedValue instanceof SerializerWrapper) {
                    SerializerWrapper<V> wrapper = (SerializerWrapper<V>) serializedValue;
                    return wrapper.getData();
                }
                return (V) serializedValue;
            }

            // 不有其它方式取数据
            if (null == supplier) {
                return null;
            }

            // 对查数据库的逻辑加锁，保证只有一个请求进行DB查询，其它连接处理于等待状态
            String lockKey = key + "_mutex", lockVal = String.valueOf(SystemClock.now());
            finalValue = redisTemplate.execute((RedisCallback<V>) connection -> {
                // 加锁成功
                if (Optional.ofNullable(connection.set(lockKey.getBytes(), lockVal.getBytes(),
                        Expiration.from(Duration.ofSeconds(30)), RedisStringCommands.SetOption.SET_IF_ABSENT)).orElse(false)) {
                    try {
                        // 通过其它方式查询数据
                        final V value = supplier.get();

                        // 防止因为网络或其它线程中断的原因导致没有获取到数据，需要快速过期
                        final long defaultExpiryTime = Objects.isNull(expirySeconds) ? TimeUnit.DAYS.toSeconds(1) : expirySeconds;
                        final long expiryTime = (null == value) ? Math.min(30, defaultExpiryTime) : defaultExpiryTime;
                        log.info("将Key:({})的数据:({})放入缓存, 过期时间: {}秒", key, value, expiryTime);
                        redisTemplate.opsForValue().set(key, SerializerWrapper.builder(value), expiryTime, TimeUnit.SECONDS);
                        return value;
                    } finally {
                        redisTemplate.delete(lockKey);
                    }
                } else {
                    try {
                        TimeUnit.MILLISECONDS.sleep(sleepTime.getAndAdd(2 * sleepTime.get()));
                    } catch (InterruptedException ex) {
                        log.warn("同步获取缓存失败", ex);
                    }
                }
                log.info("根据Key({})获取数据, 重试次数:{}, 等待时间: {}ms", key, loopCount.get(), sleepTime.get());
                return null;
            });
        }
        return finalValue;
    }
}

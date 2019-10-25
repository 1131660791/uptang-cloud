package com.uptang.cloud.sequence.util;

import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.sequence.common.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成全局唯一序号，参考了 SnowflakeIdWorker
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public final class GlobalSequenceGenerator {
    /**
     * 机器ID所占的位数
     */
    private static final long WORKER_BITS = 4;

    /**
     * 支持的最大机器ID，结果是15 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_COUNT = ~(-1L << WORKER_BITS);

    /**
     * 序列在ID中占的位数
     */
    private static final long SEQUENCE_BITS = 10;

    /**
     * 时间截向左移14位(4+10)
     */
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_BITS;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId = 0;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1;

    /**
     * 运用JVM类加载的特点，在主动引用时初始化
     */
    private static class Holder {
        private static GlobalSequenceGenerator instance = new GlobalSequenceGenerator();
    }

    public static GlobalSequenceGenerator getInstance() {
        return Holder.instance;
    }

    /**
     * 生成全局唯一序号
     *
     * @return 生成出来的序号
     */
    public long generateSequenceId() {
        return generateNextSequenceId(1);
    }

    /**
     * 生成多个全局唯一序号
     *
     * @param count 生成序号数量
     * @return 生成出来的序号
     */
    public long[] generateSequenceIds(int count) {
        long maxId = generateNextSequenceId(count);
        long[] ids = new long[count];
        for (int i = count; i > 0; i--) {
            ids[count - i] = maxId - i;
        }
        return ids;
    }

    /**
     * 私有构造函数，不允许外部 new
     * 通过系统启动的VM参数传入节点ID
     * -Dsequence.work.id=1
     */
    private GlobalSequenceGenerator() {
        this.workerId = Math.max(1, NumberUtils.toLong(System.getProperty("sequence.work.id"), 1) % MAX_WORKER_COUNT);
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long generateNextSequenceId(int count) {
        long now = System.currentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (now < lastTimestamp) {
            throw new RuntimeException("系统时间异常，请修正系统时间");
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == now) {
            sequence = (sequence + (Math.max(1, count))) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                now = generateNextMillis();
            }
        } else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = now;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((now - Constants.EPOCH_MILLI) << TIMESTAMP_SHIFT) | (workerId << SEQUENCE_BITS) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @return 当前时间戳
     */
    private long generateNextMillis() {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}

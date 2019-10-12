package com.uptang.cloud.starter.web.support;

import com.uptang.cloud.sequence.feign.SequenceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Slf4j
public class SequenceGenerator {
    private static final int QUEUE_SIZE = 100;
    private static final BlockingQueue<Long> SEQUENCE_QUEUE = new ArrayBlockingQueue<>(QUEUE_SIZE);
    private static final Lock SEQUENCE_LOCK = new ReentrantLock();

    @Autowired
    private SequenceProvider sequenceProvider;

    /**
     * 生成全局唯一ID
     *
     * @return 生成的ID
     */
    public long generateId() {
        long[] ids = generateIds(1);
        if (ArrayUtils.isEmpty(ids)) {
            return sequenceProvider.getSequence();
        }
        return ids[0];
    }

    /**
     * 生成全局唯一ID列表
     *
     * @param count 生成ID的数量
     * @return 生成的ID列表
     */
    public long[] generateIds(int count) {
        log.info("生成({})个全局唯一序列值...", count);
        if (1 == count) {
            Long sequence;
            while ((sequence = SEQUENCE_QUEUE.poll()) == null) {
                fillSequenceQueue();
            }
            return new long[]{sequence};
        } else if (count > QUEUE_SIZE) {
            return sequenceProvider.getSequences(count);
        } else {
            List<Long> sequences = new ArrayList<>();
            int gotCount = 0;
            while (gotCount < count && (gotCount += SEQUENCE_QUEUE.drainTo(sequences, count - gotCount)) < count) {
                fillSequenceQueue();
            }
            return sequences.stream().mapToLong(id -> id).toArray();
        }
    }

    /**
     * 将查询回来的数据放到序列队列中，以减少接口调用次数
     */
    private void fillSequenceQueue() {
        SEQUENCE_LOCK.lock();
        try {
            if (0 == SEQUENCE_QUEUE.size()) {
                long[] sequences = sequenceProvider.getSequences(QUEUE_SIZE);
                Arrays.stream(sequences).forEach(SEQUENCE_QUEUE::offer);
                log.info("调用服务生成({})条全局唯一序列值, ID为:[{}]", sequences.length, StringUtils.join(sequences, ","));
            }
        } finally {
            SEQUENCE_LOCK.unlock();
        }
    }
}

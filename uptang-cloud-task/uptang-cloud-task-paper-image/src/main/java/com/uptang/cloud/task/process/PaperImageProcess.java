package com.uptang.cloud.task.process;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.AtomicLongMap;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.task.common.Constants;
import com.uptang.cloud.task.service.PaperImageService;
import com.uptang.cloud.task.util.CacheKeys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Slf4j
@Component
@AllArgsConstructor
public class PaperImageProcess {
    private final PaperImageService paperImageService;
    private final StringRedisTemplate redisTemplate;

    @KafkaListener(groupId = "paper-image-process", topics = Constants.PAPER_IMAGE_TOPIC)
    public void process(List<ConsumerRecord<String, String>> records) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        // 处理进度记录计数
        AtomicLongMap<String> examCounter = AtomicLongMap.create();

        // 批量获取消息
        records.forEach(record -> {
            log.debug("createTime:{}, partition:{}, key:{}, val:{}",
                    record.timestamp(), record.partition(), record.key(), record.value());
            try {
                // 开始处理图片
                PaperImage paperImage = JSON.parseObject(record.value(), PaperImage.class);
                if (checkParameter(paperImage)) {
                    paperImageService.processImage(paperImage);

                    // 处理进度计数
                    examCounter.incrementAndGet(paperImage.getExamCode());
                }
            } catch (Exception ex) {
                log.error("处理试卷图片失败", ex);
            }
        });

        // 设置进度
        String progressKey = CacheKeys.getExamProcessProgressKey();
        examCounter.asMap().forEach((examCode, count) -> {
            redisTemplate.opsForHash().increment(progressKey, examCode, count);
        });
    }


    /**
     * 检查参数
     *
     * @param paperImage 待处理图片参数
     * @return true:检查通过
     */
    private boolean checkParameter(PaperImage paperImage) {
        if (Objects.isNull(paperImage) || CollectionUtils.isEmpty(paperImage.getSources())) {
            log.warn("待处理图片参数不正确, {}", paperImage);
            return false;
        }

        for (PaperImageSource source : paperImage.getSources()) {
            if (StringUtils.isBlank(source.getPath())) {
                return false;
            }
        }

        return true;
    }
}

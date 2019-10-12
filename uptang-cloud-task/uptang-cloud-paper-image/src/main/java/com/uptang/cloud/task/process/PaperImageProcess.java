package com.uptang.cloud.task.process;

import com.alibaba.fastjson.JSON;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.task.common.Constants;
import com.uptang.cloud.task.service.PaperImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;


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

    @KafkaListener(groupId = "paper-image-process", topics = Constants.PAPER_IMAGE_TOPIC)
    public void process(List<ConsumerRecord<String, String>> records) {
        if (CollectionUtils.isEmpty(records)) {
            return;
        }

        // 批量获取消息
        records.forEach(record -> {
            log.info("createTime:{}, partition:{}, key:{}, val:{}",
                    record.timestamp(), record.partition(), record.key(), record.value());

            try {
                // 开始处理图片
                paperImageService.processImage(JSON.parseObject(record.value(), PaperImage.class));
            } catch (Exception ex) {
                log.error("处理试卷图片失败", ex);
            }
        });
    }
}

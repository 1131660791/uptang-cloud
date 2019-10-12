package com.uptang.cloud.task.process;

import com.alibaba.fastjson.JSON;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.domain.PaperImageFormat;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.task.common.Constants;
import com.uptang.cloud.task.util.PaperFormatParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Slf4j
// @Component
public class KafkaSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final ScheduledExecutorService THREAD_POOL = new ScheduledThreadPoolExecutor(10,
            new BasicThreadFactory.Builder().namingPattern("MQ-SENDER-%d").daemon(true).build());

    private static final String[] ITEM_FORMATS = {
            "{\"pic_num\": \"21\", \"area\": {\"guid\": \"bd3e2965-1a1a-28d0-0778-105cafe9f550\", \"x1\": 1537, \"di\": 1, \"y1\": 1773, \"y\": 1297, \"x\": 110, \"pi\": \"1a\"}, \"item_name\": \"21\", \"choice_flag\": false, \"subject_code\": \"\", \"item_num\": \"21\", \"choice_num\": \"\"}",
            "{\"pic_num\": \"1\", \"area\": {\"guid\": \"fda3cab8-0b7b-53a9-5ae6-86323e72516d\", \"x1\": 1525, \"di\": 2, \"y1\": 619, \"y\": 161, \"x\": 116, \"pi\": \"1a\"}, \"item_name\": \"22\", \"choice_flag\": false, \"subject_code\": \"\", \"item_num\": \"22\", \"choice_num\": \"\"}",
            "{\"pic_num\": \"23\", \"area\": {\"guid\": \"916794ba-6801-06f7-39cd-da9aa9e4dd9a\", \"x1\": 1529, \"di\": 2, \"y1\": 1746, \"y\": 645, \"x\": 131, \"pi\": \"1a\"}, \"item_name\": \"23\", \"choice_flag\": true, \"subject_code\": \"\", \"item_num\": \"23\", \"choice_num\": \"23\"}",
            "{\"pic_num\": \"24\", \"area\": {\"guid\": \"7ff921f0-c90c-c636-b179-83cb36511a01\", \"x1\": 1541, \"di\": 2, \"y1\": 1773, \"y\": 641, \"x\": 111, \"pi\": \"1a\"}, \"item_name\": \"24\", \"choice_flag\": true, \"subject_code\": \"\", \"item_num\": \"24\", \"choice_num\": \"24\"}"
    };

    private static final String[] PAPER_IMAGES = {
            "/21/20191010/1/1_18120190918114539124_{NO}_{PAGE}.jpg",
            "/21/20191010/2/2_18120190918114539413_{PAGE}.jpg",
            "/21/20191010/3/3_18120190918114539700_{PAGE}.jpg",
            "/21/20191010/5/5_18120190918114539982_{PAGE}.jpg",
            "/21/20191010/7/7_18120190918114540270_{PAGE}.jpg",
    };


    private List<PaperImage> generatePaperImages() {
        List<PaperImageFormat> formats = Arrays.stream(ITEM_FORMATS)
                .map(PaperFormatParser::parse)
                .filter(Objects::nonNull).collect(Collectors.toList());
        PaperFormatParser.updateFilenameSuffix(formats, "a", "b");

        LongAdder index = new LongAdder();
        return Arrays.stream(PAPER_IMAGES).map(image -> {
            List<PaperImageSource> sources = formats.stream().map(format -> {
                return PaperImageSource.builder()
                        .path(image.replace("{PAGE}", format.getFilenameSuffix()))
                        .x(format.getX()).y(format.getY())
                        .width(format.getWidth()).height(format.getHeight())
                        .build();
            }).collect(Collectors.toList());

            index.add(1);
            PaperImage paperImage = generatePaperImage(index.intValue());
            paperImage.setItemNum("21~24");
            paperImage.setSources(sources);
            return paperImage;
        }).collect(Collectors.toList());
    }


    private PaperImage generatePaperImage(Integer studentId) {
        return PaperImage.builder()
                .studentId(studentId)
                .examCode("xty_20190918111914561")
                .subjectCode("1")
                .vertically(true)
                .build();
    }

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

        THREAD_POOL.scheduleAtFixedRate(() -> {
            generatePaperImages().forEach(paperImage -> {
                String message = JSON.toJSONString(paperImage);

                log.info("开始发送消息: {}", message);
                this.kafkaTemplate.send(Constants.PAPER_IMAGE_TOPIC, String.valueOf(paperImage.getStudentId()), message);
            });
        }, 0, 30, TimeUnit.MINUTES);
    }
}

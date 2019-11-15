package com.uptang.cloud.task.service.impl;

import com.obs.services.ObsClient;
import com.uptang.cloud.base.common.domain.ObsProperties;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.support.PaperImageProcessor;
import com.uptang.cloud.core.util.StringUtils;
import com.uptang.cloud.task.repository.PaperRepository;
import com.uptang.cloud.task.service.PaperImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-09
 */
@Slf4j
@Service
public class PaperImageServiceImpl implements PaperImageService {
    private final PaperImageProcessor processor;
    private final ObsProperties properties;
    private final PaperRepository paperRepository;

    public PaperImageServiceImpl(PaperImageProcessor processor, ObsProperties properties, PaperRepository paperRepository) {
        this.processor = processor;
        this.properties = properties;
        this.paperRepository = paperRepository;
    }

    @Override
    public void processImage(PaperImage paperImage) {
        long start = System.currentTimeMillis();

        // 处理图片
        BufferedImage bufferedImage = processor.processImage(paperImage);

        // 上传图片
        String relativePath = uploadImage(paperImage, bufferedImage);

        // 确保正确性，再次更新裁切标志
        paperRepository.makeAsCropped(paperImage.getExamCode(), Collections.singletonList(paperImage.getStudentId()));

        log.info("Process images[考试代码:{}, 科目代码:{}, 考号:{}] - ({}) , It's took: {}ms",
                paperImage.getExamCode(), paperImage.getSubjectCode(), paperImage.getStudentId(), relativePath,
                System.currentTimeMillis() - start);
    }


    /**
     * 将剪裁后的文件上传
     *
     * @param paperImage 用于计算文件名
     * @param image      需要上传的图片
     */
    private String uploadImage(PaperImage paperImage, BufferedImage image) {
        try {
            String relativePath = processor.generateUrlPath(paperImage);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            // 判断图片路径是否已经存在
            // getObsClient().doesObjectExist()

            getObsClient().putObject(properties.getBucketName(), processor.getObsKey(relativePath), is);
            log.debug("学生:{}, 考试码:{}, 科目码:{}, 题号:{}, 图片:{}", paperImage.getStudentId(),
                    paperImage.getExamCode(), paperImage.getSubjectCode(), paperImage.getItemNum(), relativePath);
            return relativePath;
        } catch (Exception ex) {
            log.error("上传图片异常:{}", paperImage, ex);
        }
        return StringUtils.EMPTY;
    }


    /**
     * 获致 OSS Client
     *
     * @return Oss Client
     */
    private ObsClient getObsClient() {
        return processor.getObsClient();
    }
}

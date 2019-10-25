package com.uptang.cloud.task.service.impl;

import com.obs.services.ObsClient;
import com.uptang.cloud.base.common.domain.ObsProperties;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.support.PaperImageProcessor;
import com.uptang.cloud.task.service.PaperImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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

    public PaperImageServiceImpl(PaperImageProcessor processor, ObsProperties properties) {
        this.processor = processor;
        this.properties = properties;
    }


    @Override
    public void processImage(PaperImage paperImage) {
        StopWatch stopWatch = new StopWatch("图片处理");

        stopWatch.start("处理图片");
        BufferedImage bufferedImage = processor.processImage(paperImage);
        stopWatch.stop();

        // 上传图片
        stopWatch.start("上传图片");
        uploadImage(paperImage, bufferedImage);
        stopWatch.stop();

        log.info("Process images, It's took: {}ms\n{}", stopWatch.getTotalTimeMillis(), stopWatch.prettyPrint());
    }


    /**
     * 将剪裁后的文件上传
     *
     * @param paperImage 用于计算文件名
     * @param image      需要上传的图片
     */
    private void uploadImage(PaperImage paperImage, BufferedImage image) {
        try {
            String relativePath = processor.generateUrlPath(paperImage);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            // 判断图片路径是否已经存在
            // getObsClient().doesObjectExist()

            getObsClient().putObject(properties.getBucketName(), processor.getObsKey(relativePath), is);
            log.info("学生:{}, 考试码:{}, 科目码:{}, 题号:{}, 图片:{}", paperImage.getStudentId(),
                    paperImage.getExamCode(), paperImage.getSubjectCode(), paperImage.getItemNum(), relativePath);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
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

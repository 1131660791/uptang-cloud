package com.uptang.cloud.base.common.support;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.GetObjectRequest;
import com.obs.services.model.ObsObject;
import com.uptang.cloud.base.common.domain.ObsProperties;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.util.CollectionUtils;
import com.uptang.cloud.core.util.StringUtils;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-11
 */
@Slf4j
@Component
@EnableConfigurationProperties(ObsProperties.class)
public final class PaperImageProcessor {
    /**
     * 图片剪切参数
     */
    private static final String IMAGE_CROP_TEMPLATE = "image/crop,x_%s,y_%s,w_%s,h_%s";

    /**
     * 手动创建线程池
     */
    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(5,
            10, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("DW-IMG-%d").build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * OBS Client
     */
    private static volatile ObsClient obsClient;

    /**
     * OBS 配置信息
     */
    private final ObsProperties properties;

    public PaperImageProcessor(ObsProperties properties) {
        this.properties = properties;
    }


    /**
     * OBS Key 不能以 "/" 开头
     *
     * @param filePath 文件全路径，如: /user/20170320/12345.jpg
     * @return 去掉开头的 "/"
     */
    public String getObsKey(String filePath) {
        return ('/' == filePath.charAt(0)) ? filePath.substring(1) : filePath;
    }


    /**
     * 处理图片
     *
     * @param paperImage 处理图片的参数
     * @return BufferedImage
     */
    public final BufferedImage processImage(PaperImage paperImage) {
        StopWatch stopWatch = new StopWatch("图片处理");

        // 规范化图片处理参数
        stopWatch.start("规范参数");
        normalizeImageProcessParameters(paperImage);
        stopWatch.stop();

        // 答题卡只有一个区域，只需要剪切
        if (1 == paperImage.getSources().size()) {
            stopWatch.start("剪裁图片");
            BufferedImage croppedImage = cropPaperImage(paperImage.getSources().get(0));
            stopWatch.stop();

            log.info("Crop image({}) from Huawei cloud, It's took: {}ms\n{}", paperImage.getSources().get(0).getPath(),
                    stopWatch.getLastTaskTimeMillis(), stopWatch.prettyPrint());
            return croppedImage;
        }

        // 将任务放到线程池
        stopWatch.start("生成多线程任务");
        Map<String, Future<BufferedImage>> taskMap = Maps.newHashMapWithExpectedSize(paperImage.getSources().size());
        paperImage.getSources().forEach(source -> {
            taskMap.put(generateKey(source), THREAD_POOL.submit(() -> {
                GetObjectRequest request = new GetObjectRequest(properties.getBucketName(), getObsKey(source.getPath()));
                request.setImageProcess(String.format(IMAGE_CROP_TEMPLATE, source.getX(), source.getY(), source.getWidth(), source.getHeight()));

                ObsObject obsObject = getObsClient().getObject(request);
                try (InputStream input = obsObject.getObjectContent()) {
                    return ImageIO.read(input);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    return null;
                }
            }));
            log.info("submit the task for path: {}", source.getPath());
        });
        stopWatch.stop();

        // 获取图片
        stopWatch.start("获取图片");
        List<BufferedImage> images = paperImage.getSources().stream().map(source -> {
            try {
                log.debug("Getting image from thread pool for path: {}", source.getPath());
                return taskMap.get(generateKey(source)).get(30, TimeUnit.SECONDS);
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        stopWatch.stop();

        // 画图计时
        stopWatch.start("合并图片");
        boolean vertically = Optional.ofNullable(paperImage.getVertically()).orElse(true);

        // 制作背景图
        BufferedImage canvasImage = generateCanvasImage(vertically, images);
        Graphics2D graphics = canvasImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, canvasImage.getWidth(), canvasImage.getHeight());

        // 图片拼接
        if (vertically) {
            int nextAxis = 0;
            for (BufferedImage image : images) {
                int width = image.getWidth(), height = image.getHeight();
                graphics.drawImage(image, 0, nextAxis, width, height, null);
                nextAxis += height;
            }
        } else {
            int nextAxis = 0;
            for (BufferedImage image : images) {
                int width = image.getWidth(), height = image.getHeight();
                graphics.drawImage(image, nextAxis, 0, width, height, null);
                nextAxis += width;
            }
        }

        // 刷新缓冲区
        graphics.dispose();
        canvasImage.flush();
        stopWatch.stop();

        log.info("Got {} images from Huawei cloud AND join images, It's took: {}ms\n{}",
                paperImage.getSources().size(), stopWatch.getTotalTimeMillis(), stopWatch.prettyPrint());
        return canvasImage;
    }


    /**
     * 根据考试和学生信息生成图片路径
     *  => /考试代码/科目代码/a-z0-9/SHA1(密号-考试代码-科目代码-题号).png
     *
     * @param paperImage 处理图片的参数
     * @return 图片相对路径
     */
    public final String generateUrlPath(PaperImage paperImage) {
        return generateUrlPath(paperImage.getExamCode(), paperImage.getSubjectCode(),
                paperImage.getItemNum(), paperImage.getStudentId(), paperImage.getVertically());
    }

    /**
     * 根据考试和学生信息生成图片路径
     *  => /考试代码/科目代码/a-z0-9/SHA1(密号-考试代码-科目代码-题号).png
     *
     * @param examCode    考试项目代码
     * @param subjectCode 学科代码
     * @param itemNum     题目号
     * @param studentId   学生ID/准考证号
     * @param vertically  竖拼
     * @return 图片相对路径
     */
    public final String generateUrlPath(String examCode, String subjectCode, String itemNum, String studentId, Boolean vertically) {
        if (StringUtils.isAnyBlank(examCode, subjectCode, itemNum, studentId)) {
            return null;
        }

        String[] keyFactor = {examCode, subjectCode, itemNum, studentId};
        Arrays.sort(keyFactor);
        String fileName = DigestUtils.sha1Hex(StringUtils.join(keyFactor, "-"));
        char mode = Optional.ofNullable(vertically).map(ver -> ver ? 'v' : 'h').orElse('v');

        // 考试代码/科目代码/A-Z0-9/SHA1(密号-考试代码-科目代码-题号).png
        String examCodeDir = StringUtils.substring(examCode, examCode.length() - 9);
        return String.format("/%s/%s/%s/%s-%s.png", examCodeDir, subjectCode, fileName.charAt(0), fileName, mode).toLowerCase();
    }

    /**
     * 获致 OSS Client
     *
     * @return Oss Client
     */
    public final ObsClient getObsClient() {
        if (null == obsClient) {
            synchronized (this) {
                if (null == obsClient) {
                    ObsConfiguration config = new ObsConfiguration();
                    config.setSocketTimeout(30000);
                    config.setConnectionTimeout(10000);
                    config.setEndPoint(properties.getEndPoint());
                    obsClient = new ObsClient(properties.getAccessKey(), properties.getSecretKey(), config);
                }
            }
        }
        return obsClient;
    }

    /**
     * 制作背景图
     *
     * @param vertically       是否竖拼
     * @param downloadedImages 下载的图片
     * @return 背景图
     */
    private BufferedImage generateCanvasImage(boolean vertically, List<BufferedImage> downloadedImages) {
        // 计算背景图的长宽
        Stream<Integer> imageWidthStream = downloadedImages.stream().map(BufferedImage::getWidth);
        Optional<Integer> maxWidth = vertically ? imageWidthStream.max(Integer::compareTo) : imageWidthStream.reduce(Integer::sum);

        Stream<Integer> imageHeightStream = downloadedImages.stream().map(BufferedImage::getHeight);
        Optional<Integer> maxHeight = vertically ? imageHeightStream.reduce(Integer::sum) : imageHeightStream.max(Integer::compareTo);

        // 制作背景图
        return new BufferedImage(maxWidth.orElse(0), maxHeight.orElse(0), BufferedImage.TYPE_INT_RGB);
    }

    /**
     * 通过华为云的接口裁切图片
     *
     * @param source 图片裁切参数
     * @return 裁切后的图片
     */
    private BufferedImage cropPaperImage(PaperImageSource source) {
        GetObjectRequest request = new GetObjectRequest(properties.getBucketName(), getObsKey(source.getPath()));
        request.setImageProcess(String.format(IMAGE_CROP_TEMPLATE, source.getX(), source.getY(), source.getWidth(), source.getHeight()));

        BufferedImage croppedImage = null;
        try (InputStream input = getObsClient().getObject(request).getObjectContent()) {
            croppedImage = ImageIO.read(input);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return croppedImage;
    }


    /**
     * 规范化图片处理参数
     *
     * @param paperImage 图片处理参数
     */
    private void normalizeImageProcessParameters(PaperImage paperImage) {
        // 将路径前的 '/' 去掉, 并将对应的坐标及宽高调整
        paperImage.getSources().forEach(source -> {
            source.setPath(getObsKey(source.getPath()));
            source.setX(Optional.ofNullable(source.getX()).orElse(0));
            source.setY(Optional.ofNullable(source.getY()).orElse(0));
            source.setWidth(Optional.ofNullable(source.getWidth()).orElse(Integer.MAX_VALUE));
            source.setHeight(Optional.ofNullable(source.getHeight()).orElse(Integer.MAX_VALUE));
        });

        // 将所有 path 去重
        final Set<String> existedPaths = new HashSet<>();
        for (int i = paperImage.getSources().size() - 1; i >= 0; i--) {
            PaperImageSource source = paperImage.getSources().get(i);
            if (!existedPaths.add(generateKey(source))) {
                paperImage.getSources().remove(i);
            }
        }

        // 如果没有源路径，则不进行任何处理
        if (CollectionUtils.isEmpty(paperImage.getSources())) {
            throw new BusinessException(ResponseCodeEnum.PARAMETER_REQUIRED.getCode(), "图片ID或图片路径");
        }
    }

    /**
     * 生成数据重复检查的 Key
     *
     * @param source 图片合并的参数
     * @return Key
     */
    private String generateKey(PaperImageSource source) {
        return String.format("%s|%s|%s|%s|%s", source.getPath(), source.getX(), source.getY(),
                source.getWidth(), source.getHeight());
    }
}

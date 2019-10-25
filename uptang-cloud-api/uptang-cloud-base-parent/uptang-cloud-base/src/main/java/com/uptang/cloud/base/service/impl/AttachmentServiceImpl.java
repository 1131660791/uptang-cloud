package com.uptang.cloud.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.obs.services.ObsClient;
import com.uptang.cloud.base.common.domain.ObsProperties;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.base.common.model.Attachment;
import com.uptang.cloud.base.common.support.PaperImageProcessor;
import com.uptang.cloud.base.repository.AttachmentRepository;
import com.uptang.cloud.base.service.AttachmentService;
import com.uptang.cloud.core.context.SimpleDateFormatThreadLocal;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.exception.DataAccessException;
import com.uptang.cloud.core.exception.DataNotFoundException;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.pojo.enums.StateEnum;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.support.SequenceGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Slf4j
@Service
@EnableConfigurationProperties(ObsProperties.class)
public class AttachmentServiceImpl extends ServiceImpl<AttachmentRepository, Attachment> implements AttachmentService {
    private static final Set<String> IMAGE_EXT_NAMES = Sets.newHashSet("bmp", "jpg", "jpeg", "png", "gif");
    private final SequenceGenerator sequenceGenerator;
    private final ObsProperties properties;
    private final LoadingCache<Long, Attachment> cacheByIdMap;
    private final PaperImageProcessor processor;


    @Autowired
    public AttachmentServiceImpl(SequenceGenerator sequenceGenerator, ObsProperties properties, PaperImageProcessor processor) {
        this.sequenceGenerator = sequenceGenerator;
        this.properties = properties;
        this.processor = processor;

        // 初始化缓存
        this.cacheByIdMap = Caffeine.newBuilder()
                .initialCapacity(100).maximumSize(5_000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(id -> {
                    Attachment attachment = this.getBaseMapper().selectById(id);
                    Optional.ofNullable(attachment).ifPresent(a -> attachment.setFullPath(generateFullUrl(attachment.getRelativePath())));
                    return attachment;
                });
    }

    @Override
    public Attachment load(Long id) throws DataNotFoundException {
        return Optional.ofNullable(loadSafely(id))
                .orElseThrow(() -> new DataNotFoundException(ResponseCodeEnum.DATA_NOT_FOUND.getCode(), "附件不存在"));
    }

    @Override
    public Attachment loadSafely(Long id) {
        return cacheByIdMap.get(id);
    }

    @Override
    public Map<Long, Attachment> findByIds(Long... ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return new HashMap<>(0);
        }

        Set<Long> correctedIds = Arrays.stream(ids).filter(NumberUtils::isPositive).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(correctedIds)) {
            return new HashMap<>(0);
        }

        return cacheByIdMap.getAll(correctedIds);
    }

    @Override
    public int create(Attachment... attachments) throws DataAccessException {
        Arrays.stream(attachments).forEach(attachment -> attachment.setId(sequenceGenerator.generateId()));
        Integer results = getBaseMapper().create(attachments);
        return Objects.isNull(results) ? 0 : results;
    }


    @Override
    public List<Attachment> upload(AttachmentEnum type, boolean keepOriginalFilename, MultipartFile... files) {
        if (Objects.isNull(type) || ArrayUtils.isEmpty(files)) {
            throw new BusinessException("上传的文件不正确！");
        }

        // 上传附件
        Attachment[] attachments = Stream.of(files).parallel().map(file -> {
            // 扩展名
            String extName = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = keepOriginalFilename
                    ? FilenameUtils.getBaseName(file.getOriginalFilename())
                    : DigestUtils.sha1Hex(String.valueOf(sequenceGenerator.generateId()));

            // 生成有目录格式的 OBS 文件名
            String relativePath = generateUrlPath(type, extName, fileName);
            try {
                // 上传附件
                getObsClient().putObject(properties.getBucketName(), processor.getObsKey(relativePath), file.getInputStream());

                return Attachment.builder()
                        .srcName(file.getOriginalFilename()).extName(extName)
                        .relativePath(relativePath).type(type).size(file.getSize())
                        .fullPath(generateFullUrl(relativePath))
                        .state(StateEnum.COM_ACTIVE).build();
            } catch (Exception ex) {
                log.error("上传文件({})失败！", file.getOriginalFilename());
                return null;
            }
        }).filter(Objects::nonNull).toArray(Attachment[]::new);

        // 保存附件
        if (ArrayUtils.isNotEmpty(attachments) && NumberUtils.isPositive(getBaseMapper().create(attachments))) {
            return Arrays.asList(attachments);
        }

        return new ArrayList<>(0);
    }

    /**
     * 截取图片或拼接图片
     * 图片处理： https://support.huaweicloud.com/fg-obs/obs_01_0400.html
     *
     * @param vertically   拼接模式是否：竖拼
     * @param imageSources 处理的参数
     * @return BufferedImage
     */
    @Override
    public BufferedImage processImage(boolean vertically, PaperImageSource... imageSources) {
        return processor.processImage(PaperImage.builder()
                .vertically(vertically)
                .sources(Arrays.asList(imageSources))
                .build());
    }


    @Override
    public String generateFullUrl(String relativePath) {
        if (StringUtils.isEmpty(relativePath)) {
            return StringUtils.EMPTY;
        }

        if (StringUtils.startsWith(relativePath, "http")) {
            return relativePath;
        }

        // 如果是图片，将期转换成 webp
        if (IMAGE_EXT_NAMES.contains(FilenameUtils.getExtension(relativePath))) {
            // return properties.getDomain() + relativePath + "?x-image-process=style/webp";
            return properties.getDomain() + relativePath;
        }

        return properties.getDomain() + relativePath;
    }


    /**
     * 获致 OSS Client
     *
     * @return Oss Client
     */
    private ObsClient getObsClient() {
        return processor.getObsClient();
    }


    /**
     * 生成处理后的文件路径
     * /附件类型码/日期/附件名第一个字母/附件名.扩展名
     * generateUrlPath("OTHER","jpg", "12345") => /99/20190814/1/12345.jpg
     *
     * @param type     附件类型, 如: 用户, 试卷, ...
     * @param extName  文件扩展名, 如: jpg, png, exe, ...
     * @param fileName 附件ID, 全系统中唯一的附件序列
     * @return 生成的文件路径
     */
    private String generateUrlPath(AttachmentEnum type, String extName, String fileName) {
        if (Objects.isNull(type) || StringUtils.isBlank(extName) || StringUtils.isBlank(fileName)) {
            throw new BusinessException("附件类型或附件扩展名为空！");
        }

        String dateStr = SimpleDateFormatThreadLocal.get(Constants.COMPACT_DATE_FORMAT).format(new Date(SystemClock.now()));
        extName = ('.' == extName.charAt(0)) ? extName.substring(1) : extName;
        fileName = StringUtils.trimToEmpty(fileName).toLowerCase();

        return String.format("/%s/%s/%s/%s.%s", type.getCode(), dateStr, fileName.charAt(0), fileName, extName).toLowerCase();
    }
}

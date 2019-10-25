package com.uptang.cloud.base.service;

import com.uptang.cloud.base.common.domain.ImageProcessSource;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.base.common.model.Attachment;
import com.uptang.cloud.core.exception.DataAccessException;
import com.uptang.cloud.core.exception.DataNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * 附件服务
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public interface AttachmentService {
    /**
     * 查询附件详情
     *
     * @param id 附件ID
     * @return 附件详情
     * @throws DataNotFoundException 找不到附件时抛出异常
     */
    Attachment load(Long id) throws DataNotFoundException;

    /**
     * 查询附件详情
     *
     * @param id 附件ID
     * @return 附件详情
     */
    Attachment loadSafely(Long id);

    /**
     * 根据附件ID查询
     *
     * @param ids 附件IDs
     * @return 查询到的附件
     */
    Map<Long, Attachment> findByIds(Long... ids);

    /**
     * 创建附件
     *
     * @param attachments 附件
     * @return 保存成功的数量
     * @throws DataAccessException 操作数据库异常
     */
    int create(Attachment... attachments) throws DataAccessException;

    /**
     * 上传附件
     *
     * @param type                 附件类型
     * @param keepOriginalFilename 保持原来的文件名
     * @param files                批量上传的文件
     * @return 附件信息
     */
    List<Attachment> upload(AttachmentEnum type, boolean keepOriginalFilename, MultipartFile... files);

    /**
     * 图片处理，截取，拼接
     *
     * @param vertically   拼接模式是否：竖拼
     * @param imageSources 处理的参数
     * @return BufferedImage
     */
    BufferedImage processImage(boolean vertically, PaperImageSource... imageSources);

    /**
     * 生成带有域名的附件路径
     *
     * @param relativePath 附件相对路径
     * @return 带有域名的附件路径
     */
    String generateFullUrl(String relativePath);
}

package com.uptang.cloud.base.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.base.common.model.Attachment;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Repository
public interface AttachmentRepository extends BaseMapper<Attachment> {

    /**
     * 创建附件
     *
     * @param attachments 附件
     * @return 成功写数据数据条数
     */
    Integer create(Attachment... attachments);
}

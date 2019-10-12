package com.uptang.cloud.task.service;

import com.uptang.cloud.base.common.domain.PaperImage;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-09
 */
public interface PaperImageService {
    /**
     * 图片处理，截取，拼接
     *
     * @param paperImage 处理的参数
     */
    void processImage(PaperImage paperImage);
}

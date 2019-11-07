package com.uptang.cloud.task.common;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
public interface Constants extends com.uptang.cloud.core.Constants {

    /**
     * 试卷正反面后缀
     * 0:反面, 1:正面
     */
    String[] PAPER_IMAGE_SUFFIXES = {"b", "a"};

    /**
     * 待处理试卷图片的主题
     */
    String PAPER_IMAGE_TOPIC = "paper-image";
}

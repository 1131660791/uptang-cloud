package com.uptang.cloud.base.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PaperImageFormat implements Serializable, Cloneable {
    private static final long serialVersionUID = -4996648078137024198L;

    /**
     * 题目序号
     */
    private String itemNum;

    /**
     * 图片截取X轴
     */
    private Integer x;

    /**
     * 图片截取Y轴
     */
    private Integer y;

    /**
     * 图片截取宽度
     */
    private Integer width;

    /**
     * 图片截取高度
     */
    private Integer height;

    /**
     * 答题卡页码 （1:正面，2:反面）
     */
    private Integer page;

    /**
     * 答题卡序号
     */
    private Integer cardIndex;

    /**
     * 文件名后缀
     */
    private String filenameSuffix;

    @Builder
    public PaperImageFormat(String itemNum, Integer x, Integer y, Integer width, Integer height, Integer page, Integer cardIndex) {
        this.itemNum = itemNum;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.page = page;
        this.cardIndex = cardIndex;
    }
}

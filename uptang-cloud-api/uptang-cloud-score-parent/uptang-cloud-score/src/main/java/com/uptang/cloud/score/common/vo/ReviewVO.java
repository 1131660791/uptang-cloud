package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReviewVO extends BaseVO<ReviewVO> implements Serializable, Cloneable {

    /**
     * @comment Primary key
     */
    @ApiModelProperty(notes = "审议进度ID")
    private Long id;

    /**
     * @comment 成绩ID/成绩归档ID
     */
    @ApiModelProperty(notes = "成绩ID/成绩归档ID")
    private Long scoreId;

    /**
     * @comment 类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private Integer type;

    @ApiModelProperty(notes = "成绩类型 文本")
    private String typeText;

    /**
     * @comment 公示 0 无状态 1 公示期 2 撤销公示 3 DONE
     */
    @ApiModelProperty(notes = "公示 0 无状态 1 公示期 2 撤销公示 3 DONE")
    private Integer showStat;

    @ApiModelProperty(notes = "公示文本")
    private String showStatText;

    /**
     * @comment 异议 0 无状态 1 异议处理期 2 DONE
     */
    @ApiModelProperty(notes = "异议 0 无状态 1 异议处理期 2 DONE")
    private Integer objection;

    @ApiModelProperty(notes = "异议文本")
    private String objectionText;

    /**
     * @comment 异议文本, 异议描述
     */
    @ApiModelProperty(notes = "异议描述")
    private String objectionDesc;

    /**
     * @comment 归档 0 已提交 1 已归档 2 撤销归档
     */
    @ApiModelProperty(notes = "归档 0 已提交 1 已归档 2 撤销归档")
    private Integer archive;

    /**
     * @comment 归档 0 已提交 1 已归档 2 撤销归档
     */
    @ApiModelProperty(notes = "归档文本")
    private String archiveText;

    /**
     * @comment 创建时间
     */
    private java.util.Date createdTime;

    /**
     * @comment 修改时间
     */
    private java.util.Date updatedTime;
}


package com.uptang.cloud.score.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:44
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectionRecordVO extends BaseVO<ObjectionRecordVO> implements Serializable, Cloneable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @ApiModelProperty("异议ID")
    private Long id;

    /**
     * @type bigint(20) unsigned
     * @comment 履历ID
     * @Column(name = "resume_id")
     */
    @ApiModelProperty("履历ID")
    private Long resumeId;

    /**
     * @type tinyint(1) unsigned
     * @comment 异议状态 1 异议处理期 2 完成
     * @Column(name = "state")
     */
    @ApiModelProperty("异议状态 1 异议处理期 2 完成")
    private ObjectionEnum state;

    /**
     * @type varchar(255)
     * @comment 异议文本, 异议描述
     * @Column(name = "description")
     */
    @ApiModelProperty("异议文本, 异议描述")
    private String description;

    /**
     * @type tinyint(1) unsigned
     * @comment 审核状态 1 通过 2 拒绝
     * @Column(name = "review_stat")
     */
    @ApiModelProperty("审核状态 1 通过 2 拒绝")
    private ReviewEnum reviewStat;

    /**
     * @type varchar(255)
     * @comment 审核信息
     * @Column(name = "review_desc")
     */
    @ApiModelProperty("审核意见")
    private String reviewDesc;

    /**
     * @type bigint(20) unsigned
     * @comment 审核人
     * @Column(name = "review_id")
     */
    @ApiModelProperty("审核人")
    private Long reviewId;

    /**
     * @type bigint(20) unsigned
     * @comment 异议创建人
     * @Column(name = "creator_id")
     */
    @ApiModelProperty("审核人")
    private Long creatorId;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     * @Column(name = "score_type")
     */
    @ApiModelProperty("成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private ScoreTypeEnum scoreType;

    /**
     * @type timestamp
     * @comment 创建时间
     * @Column(name = "created_time")
     */
    @ApiModelProperty("异议创建时间")
    private java.util.Date createdTime;

    /**
     * @type timestamp
     * @comment 审核时间
     * @Column(name = "modified_time")
     */
    @ApiModelProperty("异议审核时间")
    private java.util.Date modifiedTime;
}

package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("objection_record")
public class ObjectionRecord implements Serializable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * @type bigint(20) unsigned
     * @comment 履历ID
     * @Column(name = "resume_id")
     */
    private Long resumeId;

    /**
     * @type tinyint(1) unsigned
     * @comment 异议状态 1 异议处理期 2 完成
     * @Column(name = "state")
     */
    private ObjectionEnum state;

    /**
     * @type varchar(255)
     * @comment 异议文本, 异议描述
     * @Column(name = "description")
     */
    private String description;

    /**
     * @type tinyint(1) unsigned
     * @comment 审核状态 1 通过 2 拒绝
     * @Column(name = "review_stat")
     */
    private ReviewEnum reviewStat;

    /**
     * @type varchar(255)
     * @comment 审核信息
     * @Column(name = "review_desc")
     */
    private String reviewDesc;

    /**
     * @type bigint(20) unsigned
     * @comment 审核人
     * @Column(name = "review_id")
     */
    private Long reviewId;

    /**
     * @type bigint(20) unsigned
     * @comment 异议创建人
     * @Column(name = "creator_id")
     */
    private Long creatorId;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     * @Column(name = "score_type")
     */
    private ScoreTypeEnum scoreType;

    /**
     * @type timestamp
     * @comment 创建时间
     * @Column(name = "created_time")
     */
    private java.util.Date createdTime;

    /**
     * @type timestamp
     * @comment 审核时间
     * @Column(name = "modified_time")
     */
    private java.util.Date modifiedTime;
}
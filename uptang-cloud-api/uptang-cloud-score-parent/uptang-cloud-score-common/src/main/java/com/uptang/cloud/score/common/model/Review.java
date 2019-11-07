package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.score.common.enums.ArchiveEnum;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.ShowStatEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : 审议进度表
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("review")
public class Review implements Serializable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * @type bigint(20) unsigned
     * @comment 成绩ID/成绩归档ID
     */
    private Long scoreId;

    /**
     * @type tinyint(1) unsigned
     * @comment 类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    private ScoreTypeEnum type;

    /**
     * @type tinyint(1) unsigned
     * @comment 公示 0 无状态 1 公示期 2 撤销公示 3 DONE
     */
    private ShowStatEnum showStat;

    /**
     * @type tinyint(1) unsigned
     * @comment 异议 0 无状态 1 异议处理期 2 DONE
     */
    private ObjectionEnum objection;

    /**
     * @type varchar(255)
     * @comment 异议文本, 异议描述
     */
    private String objectionDesc;

    /**
     * @type tinyint(1) unsigned
     * @comment 归档 0 已提交 1 已归档 2 撤销归档
     */
    private ArchiveEnum archive;

    /**
     * @type timestamp
     * @comment 创建时间
     */
    private java.util.Date createdTime;

    /**
     * @type timestamp
     * @comment 修改时间
     */
    private java.util.Date updatedTime;
}


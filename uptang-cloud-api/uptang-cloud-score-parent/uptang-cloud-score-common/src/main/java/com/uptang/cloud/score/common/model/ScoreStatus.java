package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : 状态表
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("score_status")
public class ScoreStatus implements Serializable {

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
     * @comment 公示 1 公示中 2 归档 3 已提交
     * @Column(name = "state")
     */
    private ScoreStatusEnum state;

    /**
     * @type timestamp
     * @comment 公示开始时间[只有在”公示中“才会有开始时间]
     * @Column(name = "started_time")
     */
    private java.util.Date startedTime;

    /**
     * @type timestamp
     * @comment 公示结束时间[只有在”归档“才会有开始时间]
     * @Column(name = "finish_time")
     */
    private java.util.Date finishTime;
}


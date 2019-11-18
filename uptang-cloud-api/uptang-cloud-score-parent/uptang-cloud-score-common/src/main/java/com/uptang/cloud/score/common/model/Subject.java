package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
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
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
@TableName("logic_subject")
public class Subject implements Serializable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * @type bigint(20) unsigned
     * @comment 履历表ID
     * @Column(name = "resume_id")
     */
    private Long resumeId;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     * @Column(name = "score_type")
     */
    private ScoreTypeEnum scoreType;

    /**
     * @type smallint(3) unsigned
     * @comment 科目编号
     * @Column(name = "code")
     */
    private Integer code;

    /**
     * @type varchar(20)
     * @comment 科目名称
     * @Column(name = "name")
     */
    private String name;

    /**
     * @type varchar(20)
     * @comment 文本成绩
     * @Column(name = "score_text")
     */
    private String scoreText;

    /**
     * @type smallint(5) unsigned
     * @comment 数字成绩
     * @Column(name = "score_number")
     */
    private Integer scoreNumber;

    private transient Long studentId;
}


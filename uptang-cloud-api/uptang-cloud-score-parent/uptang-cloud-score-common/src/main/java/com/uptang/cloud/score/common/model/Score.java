package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SubjectEnum;
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
@TableName("logic_score")
public class Score implements Serializable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    private ScoreTypeEnum type;

    /**
     * @type tinyint(1) unsigned
     * @comment 科目 数学 语文 英语 物理 地理 化学 (0 表示没有科目)
     */
    private SubjectEnum subject;

    /**
     * @type varchar(20)
     * @comment 文本成绩 E.g: 合格、不合格
     */
    private String scoreText;

    /**
     * @type smallint(4)
     * @comment 数字成绩 E.g: 85.2 (0 表示没有成绩)
     */
    private Integer scoreNumber;
}


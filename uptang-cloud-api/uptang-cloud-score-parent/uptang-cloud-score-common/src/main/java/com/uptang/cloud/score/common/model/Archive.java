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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("archive")
public class Archive implements Serializable {

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
     * @type varchar(255)
     * @comment 成绩详情 {语文:85.5,数学:85.5,英语:89.5}
     */
    private String detail;
}


package com.uptang.cloud.score.common.model;

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
 * @createtime : 2019-11-06 16:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Show extends AcademicResume implements Serializable {

    /**
     * 审议进度ID
     */
    private Long reviewId;

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
}
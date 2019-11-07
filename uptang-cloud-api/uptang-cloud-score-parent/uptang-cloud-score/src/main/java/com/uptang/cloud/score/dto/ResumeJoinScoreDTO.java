package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.enums.SubjectEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 15:57
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResumeJoinScoreDTO extends AcademicResume implements Serializable {

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

package com.uptang.cloud.score.dto;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 16:57
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class SubjectDTO {

    private Long subjectId;

    /**
     * 履历表ID
     */
    private Long resumeId;

    /**
     * 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    private ScoreTypeEnum scoreType;

    /**
     * 科目编号
     */
    private Integer code;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 文本成绩
     */
    private String scoreText;

    /**
     * 数字成绩
     */
    private Integer scoreNumber;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 学校名
     */
    private String schoolName;

    /**
     * 学期
     */
    private Long semesterId;

    /**
     * 班级
     */
    private Long classId;

    /**
     * 年级
     */
    private Long gradeId;

    /**
     * 学校
     */
    private Long schoolId;

    private GenderEnum gender;

    private Date createdTime;

    private Date modifiedTime;
}

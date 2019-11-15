package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 17:18
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SubjectVO extends BaseVO<SubjectVO> implements Serializable, Cloneable {

    /**
     * 科目ID
     */
    @ApiModelProperty(notes = "科目ID")
    private Long subjectId;

    /**
     * 履历表ID
     */
    @ApiModelProperty(notes = "履历表ID")
    private Long resumeId;

    /**
     * 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private ScoreTypeEnum scoreType;

    /**
     * 科目编号
     */
    @ApiModelProperty(notes = "科目编号")
    private Integer code;

    /**
     * 科目名称
     */
    @ApiModelProperty(notes = "科目名称")
    private String name;

    /**
     * 文本成绩
     */
    @ApiModelProperty(notes = "文本成绩")
    private String scoreText;

    /**
     * 数字成绩
     */
    @ApiModelProperty(notes = "数字成绩")
    private Double scoreNumber;

    /**
     * 学生ID
     */
    @ApiModelProperty(notes = "学生ID")
    private Long studentId;

    /**
     * 班级名称
     */
    @ApiModelProperty(notes = "班级名称")
    private String className;

    /**
     * 年级名称
     */
    @ApiModelProperty(notes = "年级名称")
    private String gradeName;

    /**
     * 学校名称
     */
    @ApiModelProperty(notes = "学校名称")
    private String schoolName;

    /**
     * 学期
     */
    @ApiModelProperty(notes = "学校ID")
    private Long semesterId;

    /**
     * 班级
     */
    @ApiModelProperty(notes = "班级ID")
    private Long classId;

    /**
     * 年级
     */
    @ApiModelProperty(notes = "年级ID")
    private Long gradeId;

    /**
     * 学校ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long schoolId;

    @ApiModelProperty(notes = "创建时间")
    private Date createdTime;

    @ApiModelProperty(notes = "更新时间")
    private Date modifiedTime;
}

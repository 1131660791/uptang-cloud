package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
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
public class AcademicResumeVO extends BaseVO<AcademicResumeVO> implements Serializable, Cloneable  {

    /**
     * @comment Primary key
     */
    @ApiModelProperty(notes = "学业成绩ID")
    private Long id;

    /**
     * @comment 学校名称
     */
    @ApiModelProperty(notes = "学校名称")
    private String school;

    /**
     * @comment 学校ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long schoolId;

    /**
     * @comment 年级编号
     */
    @ApiModelProperty(notes = "年级编号")
    private Long gradeId;

    /**
     * @comment 年级名称
     */
    @ApiModelProperty(notes = "年级名称")
    private String gradeName;

    /**
     * @comment 班级编号
     */
    @ApiModelProperty(notes = "班级编号")
    private Long classId;

    /**
     * @comment 班级名称
     */
    @ApiModelProperty(notes = "班级名称")
    private String className;

    /**
     * @comment 学期 0 上半学期 1 下班学期
     */
    @ApiModelProperty(notes = "学期编码 0 上半学期 1 下班学期")
    private Integer semesterCode;

    @ApiModelProperty(notes = "学期")
    private String semesterCodeText;

    /**
     * @comment 学期名称
     */
    @ApiModelProperty(notes = "学期名称")
    private String semesterName;

    /**
     * @comment 学生姓名
     */
    @ApiModelProperty(notes = "学生姓名")
    private String studentName;

    /**
     * @comment 学籍号 E.g: G511402200509048827
     */
    @ApiModelProperty(notes = "学籍号")
    private String studentCode;

    /**
     * @comment 学生性别 0 为止性别 1 男 2 女 9 未说明性别
     */
    @ApiModelProperty(notes = "学生性别 0 为止性别 1 男 2 女 9 未说明性别")
    private Integer gender;

    /**
     * @comment 学生性别 0 为止性别 1 男 2 女 9 未说明性别
     */
    @ApiModelProperty(notes = "学生性别")
    private String genderText;

    /**
     * @comment 成绩表ID
     */
    @ApiModelProperty(notes = "成绩ID")
    private Long scoreId;

    /**
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型编码 0 学业成绩 1 体质健康 2 艺术成绩")
    private Integer scoreType;

    /**
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private String scoreTypeText;

    /**
     * @comment 创建人ID
     */
    @ApiModelProperty(notes = "创建人ID")
    private Long createdFounderId;

    /**
     * @comment 修改人ID
     */
    @ApiModelProperty(notes = "修改人ID")
    private Long updatedFounderId;

    /**
     * @comment 创建时间
     */
    private java.util.Date createdTime;

    /**
     * @comment 修改时间
     */
    private java.util.Date updatedTime;
}


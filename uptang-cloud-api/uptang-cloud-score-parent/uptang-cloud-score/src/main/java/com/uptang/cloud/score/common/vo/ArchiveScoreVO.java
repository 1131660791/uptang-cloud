package com.uptang.cloud.score.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 17:12
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveScoreVO extends BaseVO<ArchiveScoreVO> implements Serializable, Cloneable {

    @ApiModelProperty(notes = "归档ID")
    private Long id;

    @ApiModelProperty(notes = "创建时间")
    private Date createdTime;

    @ApiModelProperty(notes = "更新时间")
    private Date modifiedTime;

    /**
     * 学生性别
     */
    @ApiModelProperty(notes = "学生性别 1 男 2 女")
    private GenderEnum gender;

    /**
     * 学生姓名
     */
    @ApiModelProperty(notes = "学生姓名")
    private String name;

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
    @ApiModelProperty(notes = "学期ID")
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
     * 学校
     */
    @ApiModelProperty(notes = "学校ID")
    private Long schoolId;

    /**
     * 履历ID
     */
    @ApiModelProperty(notes = "履历ID")
    private Long resumeId;

    /**
     * 学生ID
     */
    @ApiModelProperty(notes = "学生ID")
    private Long studentId;

    /**
     * 归档数据
     */
    @ApiModelProperty(notes = "归档数据")
    private String detail;


    /**
     * 成绩类型
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private ScoreTypeEnum type;
}

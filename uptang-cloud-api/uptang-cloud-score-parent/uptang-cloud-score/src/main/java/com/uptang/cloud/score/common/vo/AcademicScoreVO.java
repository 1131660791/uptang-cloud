package com.uptang.cloud.score.common.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 9:30
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AcademicScoreVO extends BaseVO<AcademicScoreVO> implements Serializable, Cloneable {

    /**
     * 年级
     */
    @ApiModelProperty(notes = "年级")
    @ExcelProperty(value = "年级")
    private String gradeCode;

    /**
     * 班级
     */
    @ApiModelProperty(notes = "班级")
    @ExcelProperty(value = "班级")
    private Integer classCode;

    /**
     * 学籍号
     */
    @ApiModelProperty(notes = "学籍号")
    @ExcelProperty(value = "学籍号")
    private String studentCode;

    /**
     * 姓名
     */
    @ApiModelProperty(notes = "姓名")
    @ExcelProperty(value = "姓名")
    private String studentName;

    /**
     * 道德与法治
     */
    @ApiModelProperty(notes = "道德与法治")
    @ExcelProperty(value = "道德与法治")
    private Double moralityLaw;

    /**
     * 语文
     */
    @ApiModelProperty(notes = "语文")
    @ExcelProperty(value = "语文")
    private Double chinese;

    /**
     * 数学
     */
    @ApiModelProperty(notes = "数学")
    @ExcelProperty(value = "数学")
    private Double math;

    /**
     * 英语
     */
    @ApiModelProperty(notes = "英语")
    @ExcelProperty(value = "英语")
    private Double english;

    /**
     * 物理
     */
    @ApiModelProperty(notes = "物理")
    @ExcelProperty(value = "物理")
    private Double physical;


    /**
     * 化学
     */
    @ApiModelProperty(notes = "化学")
    @ExcelProperty(value = "化学")
    private Double chemistry;

    /**
     * 历史
     */
    @ApiModelProperty(notes = "历史")
    @ExcelProperty(value = "历史")
    private Double history;

    /**
     * 地理
     */
    @ApiModelProperty(notes = "地理")
    @ExcelProperty(value = "地理")
    private Double geography;


    /**
     * 生物
     */
    @ApiModelProperty(notes = "生物")
    @ExcelProperty(value = "生物")
    private Double biological;

    /**
     * 体育
     */
    @ApiModelProperty(notes = "体育")
    @ExcelProperty(value = "体育")
    private Double physicalEducation;

    /**
     * 信息技术
     */
    @ApiModelProperty(notes = "信息技术")
    @ExcelProperty(value = "信息技术")
    private String technology;

    /**
     * 音乐
     */
    @ApiModelProperty(notes = "音乐")
    @ExcelProperty(value = "音乐")
    private String music;

    /**
     * 美术
     */
    @ApiModelProperty(notes = "美术")
    @ExcelProperty(value = "美术")
    private String art;

    //

    /**
     * 物理实验
     */
    @ApiModelProperty(notes = "物理实验")
    @ExcelProperty(value = "物理实验")
    private String physicalExperiment;

    /**
     * 化学实验
     */
    @ApiModelProperty(notes = "化学实验")
    @ExcelProperty(value = "化学实验")
    private String chemistryExperiment;

    /**
     * 生物实验
     */
    @ApiModelProperty(notes = "生物实验")
    @ExcelProperty(value = "生物实验")
    private String biologicalExperiments;

    /**
     * 劳动与技术教育
     */
    @ApiModelProperty(notes = "劳动与技术教育")
    @ExcelProperty(value = "劳动与技术教育")
    private String labor;

    /**
     * 地方及校本课程
     */
    @ApiModelProperty(notes = "地方及校本课程")
    @ExcelProperty(value = "地方及校本课程")
    private String localCourse;
}

package com.uptang.cloud.score.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 9:49
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 * <p>
 * {0=年级, 1=班级, 2=学籍号, 3=姓名, 4=道德与法治, 5=语文, 6=数学, 7=英语, 8=物理,
 * 9=化学, 10=历史, 11=地理, 12=生物, 13=体育, 14=信息技术, 15=音乐, 16=美术, 17=物理实验, 18=化学实验, 19=生物实验, 20=劳动与技术教育, 21=地方及校本课程}
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AcademicScoreDTO implements Serializable {

    /**
     * 年级
     */
    @ExcelProperty(value = "年级", index = 0)
    private String gradeCode;

    /**
     * 班级
     */
    @ExcelProperty(value = "班级", index = 1)
    private Integer classCode;

    /**
     * 学籍号
     */
    @ExcelProperty(value = "学籍号", index = 2)
    private String studentCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 3)
    private String studentName;

    /**
     * 道德与法治
     */
    @ExcelProperty(value = "道德与法治", index = 4)
    private Double moralityLaw;

    /**
     * 语文
     */
    @ExcelProperty(value = "语文", index = 5)
    private Double chinese;

    /**
     * 数学
     */
    @ExcelProperty(value = "数学", index = 6)
    private Double math;

    /**
     * 英语
     */
    @ExcelProperty(value = "英语", index = 7)
    private Double english;

    /**
     * 物理
     */
    @ExcelProperty(value = "物理", index = 8)
    private Double physical;

    /**
     * 化学
     */
    @ExcelProperty(value = "化学", index = 9)
    private Double chemistry;

    /**
     * 历史
     */
    @ExcelProperty(value = "历史", index = 10)
    private Double history;

    /**
     * 地理
     */
    @ExcelProperty(value = "地理", index = 11)
    private Double geography;

    /**
     * 生物
     */
    @ExcelProperty(value = "生物", index = 12)
    private Double biological;

    /**
     * 体育
     */
    @ExcelProperty(value = "体育", index = 13)
    private Double physicalEducation;

    /**
     * 信息技术
     */
    @ExcelProperty(value = "信息技术", index = 14)
    private String technology;

    /**
     * 音乐
     */
    @ExcelProperty(value = "音乐", index = 15)
    private String music;

    /**
     * 美术
     */
    @ExcelProperty(value = "美术", index = 16)
    private String art;

    /**
     * 物理实验
     */
    @ExcelProperty(value = "物理实验", index = 17)
    private String physicalExperiment;

    /**
     * 化学实验
     */
    @ExcelProperty(value = "化学实验", index = 18)
    private String chemistryExperiment;

    /**
     * 生物实验
     */
    @ExcelProperty(value = "生物实验", index = 19)
    private String biologicalExperiments;

    /**
     * 劳动与技术教育
     */
    @ExcelProperty(value = "劳动与技术教育", index = 20)
    private String labor;

    /**
     * 地方及校本课程
     */
    @ExcelProperty(value = "地方及校本课程", index = 21)
    private String localCourse;
}
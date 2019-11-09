package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.enums.InformationTechnologyEnum;
import com.uptang.cloud.score.common.enums.LevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 9:49
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AcademicScoreDTO implements Serializable {

    /**
     * 年级
     */
    private String gradeCode;

    /**
     * 班级
     */
    private Integer classCode;

    /**
     * 学籍号
     */
    private String studentCode;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 道德与法治
     */
    private Double moralityLaw;

    /**
     * 语文
     */
    private Double chinese;

    /**
     * 数学
     */
    private Double math;

    /**
     * 英语
     */
    private Integer english;

    /**
     * 物理
     */
    private Integer physical;

    /**
     * 化学
     */
    private Integer chemistry;

    /**
     * 历史
     */
    private Integer history;

    /**
     * 地理
     */
    private Integer geography;

    /**
     * 生物
     */
    private Integer biological;

    /**
     * 体育
     */
    private Integer physicalEducation;

    /**
     * 信息技术
     */
    private InformationTechnologyEnum technology;

    /**
     * 音乐
     */
    private LevelEnum music;

    /**
     * 美术
     */
    private LevelEnum art;

    /**
     * 物理实验
     */
    private LevelEnum physicalExperiment;

    /**
     * 化学实验
     */
    private LevelEnum chemistryExperiment;

    /**
     * 生物实验
     */
    private LevelEnum biologicalExperiments;

    /**
     * 劳动与技术教育
     */
    private LevelEnum labor;

    /**
     * 地方及校本课程
     */
    private LevelEnum localCourse;

    @Override
    public String toString() {
        return "{\"AcademicScoreDTO\":{"
                + "                        \"gradeCode\":\"" + gradeCode + "\""
                + ",                         \"classCode\":\"" + classCode + "\""
                + ",                         \"studentCode\":\"" + studentCode + "\""
                + ",                         \"studentName\":\"" + studentName + "\""
                + ",                         \"moralityLaw\":\"" + moralityLaw + "\""
                + ",                         \"chinese\":\"" + chinese + "\""
                + ",                         \"math\":\"" + math + "\""
                + ",                         \"english\":\"" + english + "\""
                + ",                         \"physical\":\"" + physical + "\""
                + ",                         \"chemistry\":\"" + chemistry + "\""
                + ",                         \"history\":\"" + history + "\""
                + ",                         \"geography\":\"" + geography + "\""
                + ",                         \"biological\":\"" + biological + "\""
                + ",                         \"physicalEducation\":\"" + physicalEducation + "\""
                + ",                         \"technology\":\"" + technology + "\""
                + ",                         \"music\":\"" + music + "\""
                + ",                         \"art\":\"" + art + "\""
                + ",                         \"physicalExperiment\":\"" + physicalExperiment + "\""
                + ",                         \"chemistryExperiment\":\"" + chemistryExperiment + "\""
                + ",                         \"biologicalExperiments\":\"" + biologicalExperiments + "\""
                + ",                         \"labor\":\"" + labor + "\""
                + ",                         \"localCourse\":\"" + localCourse + "\""
                + "}}";
    }
}

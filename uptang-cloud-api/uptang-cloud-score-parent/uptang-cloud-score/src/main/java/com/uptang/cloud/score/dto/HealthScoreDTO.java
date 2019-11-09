package com.uptang.cloud.score.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.LevelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:08
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HealthScoreDTO implements Serializable {

    /**
     * 年级编号
     */
    private String gradeCode;

    /**
     * 班级编号
     */
    private Integer classCode;

    /**
     * 班级名称
     */
    private String gradeName;

    /**
     * 学籍号
     */
    private String studentCode;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 家庭住址
     */
    private String homeAddress;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Integer bodyWeight;

    /**
     * 体重评分
     */
    private Integer bodyWeightScore;

    /**
     * 体重等级
     */
    private LevelEnum bodyWeightLevel;

    /**
     * 肺活量
     */
    private Integer vitalCapacity;

    /**
     * 肺活量评分
     */
    private Integer vitalCapacityScore;

    /**
     * 肺活量等级
     */
    private LevelEnum vitalCapacityLevel;

    /**
     * 50米跑
     */
    private Integer fiftyMetersRun;

    /**
     * 50米跑评分
     */
    private Integer fiftyMetersScore;

    /**
     * 50米跑等级
     */
    private LevelEnum fiftyMetersLevel;

    /**
     * 立定跳远
     */
    private Integer standingLongJump;

    /**
     * 立定跳远评分
     */
    private Integer standingLongJumpScore;

    /**
     * 立定跳远等级
     */
    private LevelEnum standingLongJumpLevel;

    /**
     * 坐位体前屈
     */
    private Integer flexion;

    /**
     * 坐位体前屈评分
     */
    private Integer flexionScore;

    /**
     * 坐位体前屈等级
     */
    private LevelEnum flexionLevel;

    /**
     * 800米跑
     */
    private String eightHundredMeters;

    /**
     * 800米跑评分
     */
    private Integer eightHundredMetersScore;

    /**
     * 800米跑等级
     */
    private LevelEnum eightHundredMetersLevel;

    /**
     * 800米跑附加分
     */
    private Integer additionalPoints800;

    /**
     * 1000米跑
     */
    private String oneKilometerMeters;

    /**
     * 1000米跑评分
     */
    private Integer oneKilometerMetersScore;

    /**
     * 1000米跑等级
     */
    private LevelEnum oneKilometerMetersLevel;

    /**
     * 1000米跑附加分
     */
    private Integer additionalPoints1000;

    /**
     * 一分钟仰卧起坐
     */
    private String sitUp;

    /**
     * 一分钟仰卧起坐评分
     */
    private Integer sitUpScore;

    /**
     * 1000米跑等级
     */
    private LevelEnum sitUpLevel;

    /**
     * 一分钟仰卧起坐附加分
     */
    private Integer sitUpAdditionalPoints;

    /**
     * 引体向上
     */
    private String pullUp;

    /**
     * 引体向上评分
     */
    private Integer pullUpScore;

    /**
     * 引体向上等级
     */
    private LevelEnum pullUpLevel;

    /**
     * 引体向上附加分
     */
    private Integer pullUpAdditionalPoints;

    /**
     * 标准分
     */
    private Integer standardScore;

    /**
     * 附加分
     */
    private Integer additionalPoints;

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 总分等级
     */
    private LevelEnum totalScoreLevel;
}

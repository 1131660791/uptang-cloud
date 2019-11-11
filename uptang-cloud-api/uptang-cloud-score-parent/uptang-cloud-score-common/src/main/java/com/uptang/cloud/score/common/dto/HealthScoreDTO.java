package com.uptang.cloud.score.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "年级编号", index = 0)
    private String gradeCode;

    /**
     * 班级编号
     */
    @ExcelProperty(value = "班级编号", index = 1)
    private Integer classCode;

    /**
     * 班级名称
     */
    @ExcelProperty(value = "班级名称", index = 2)
    private String gradeName;

    /**
     * 学籍号
     */
    @ExcelProperty(value = "学籍号", index = 3)
    private String studentCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 5)
    private String studentName;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 6)
    private String gender;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 7)
    private Date birth;

    /**
     * 家庭住址
     */
    @ExcelProperty(value = "家庭住址", index = 8)
    private String homeAddress;

    /**
     * 身高
     */
    @ExcelProperty(value = "身高", index = 9)
    private Double height;

    /**
     * 体重
     */
    @ExcelProperty(value = "体重", index = 10)
    private Double bodyWeight;

    /**
     * 体重评分
     */
    @ExcelProperty(value = "体重评分", index = 11)
    private Double bodyWeightScore;

    /**
     * 体重等级
     */
    @ExcelProperty(value = "体重等级", index = 12)
    private String bodyWeightLevel;

    /**
     * 肺活量
     */
    @ExcelProperty(value = "肺活量", index = 13)
    private Double vitalCapacity;

    /**
     * 肺活量评分
     */
    @ExcelProperty(value = "肺活量评分", index = 14)
    private Double vitalCapacityScore;

    /**
     * 肺活量等级
     */
    @ExcelProperty(value = "肺活量等级", index = 15)
    private String vitalCapacityLevel;

    /**
     * 50米跑
     */
    @ExcelProperty(value = "50米跑", index = 16)
    private Double fiftyMetersRun;

    /**
     * 50米跑评分
     */
    @ExcelProperty(value = "50米跑评分", index = 17)
    private Double fiftyMetersScore;

    /**
     * 50米跑等级
     */
    @ExcelProperty(value = "50米跑等级", index = 18)
    private String fiftyMetersLevel;

    /**
     * 立定跳远
     */
    @ExcelProperty(value = "立定跳远", index = 19)
    private Double standingLongJump;

    /**
     * 立定跳远评分
     */
    @ExcelProperty(value = "立定跳远评分", index = 20)
    private Double standingLongJumpScore;

    /**
     * 立定跳远等级
     */
    @ExcelProperty(value = "立定跳远等级", index = 21)
    private String standingLongJumpLevel;

    /**
     * 坐位体前屈
     */
    @ExcelProperty(value = "坐位体前屈", index = 22)
    private Double flexion;

    /**
     * 坐位体前屈评分
     */
    @ExcelProperty(value = "坐位体前屈评分", index = 23)
    private Double flexionScore;

    /**
     * 坐位体前屈等级
     */
    @ExcelProperty(value = "坐位体前屈等级", index = 24)
    private String flexionLevel;

    /**
     * 800米跑
     */
    @ExcelProperty(value = "800米跑", index = 25)
    private String eightHundredMeters;

    /**
     * 800米跑评分
     */
    @ExcelProperty(value = "800米跑评分", index = 26)
    private Double eightHundredMetersScore;

    /**
     * 800米跑等级
     */
    @ExcelProperty(value = "800米跑等级", index = 27)
    private String eightHundredMetersLevel;

    /**
     * 800米跑附加分
     */
    @ExcelProperty(value = "800米跑附加分", index = 28)
    private Double additionalPoints800;

    /**
     * 1000米跑
     */
    @ExcelProperty(value = "1000米跑", index = 29)
    private String oneKilometerMeters;

    /**
     * 1000米跑评分
     */
    @ExcelProperty(value = "1000米跑评分", index = 30)
    private Double oneKilometerMetersScore;

    /**
     * 1000米跑等级
     */
    @ExcelProperty(value = "1000米跑等级", index = 31)
    private String oneKilometerMetersLevel;

    /**
     * 1000米跑附加分
     */
    @ExcelProperty(value = "1000米跑附加分", index = 32)
    private Double additionalPoints1000;

    /**
     * 一分钟仰卧起坐
     */
    @ExcelProperty(value = "一分钟仰卧起坐", index = 33)
    private String sitUp;

    /**
     * 一分钟仰卧起坐评分
     */
    @ExcelProperty(value = "一分钟仰卧起坐评分", index = 34)
    private Double sitUpScore;

    /**
     * 1000米跑等级
     */
    @ExcelProperty(value = "一分钟仰卧起坐等级", index = 35)
    private String sitUpLevel;

    /**
     * 一分钟仰卧起坐附加分
     */
    @ExcelProperty(value = "一分钟仰卧起坐附加分", index = 36)
    private Double sitUpAdditionalPoints;

    /**
     * 引体向上
     */
    @ExcelProperty(value = "引体向上", index = 37)
    private String pullUp;

    /**
     * 引体向上评分
     */
    @ExcelProperty(value = "引体向上评分", index = 38)
    private Double pullUpScore;

    /**
     * 引体向上等级
     */
    @ExcelProperty(value = "引体向上等级", index = 39)
    private String pullUpLevel;

    /**
     * 引体向上附加分
     */
    @ExcelProperty(value = "引体向上附加分", index = 40)
    private Double pullUpAdditionalPoints;

    /**
     * 标准分
     */
    @ExcelProperty(value = "标准分", index = 41)
    private Double standardScore;

    /**
     * 附加分
     */
    @ExcelProperty(value = "附加分", index = 42)
    private Double additionalPoints;

    /**
     * 总分
     */
    @ExcelProperty(value = "总分", index = 43)
    private Double totalScore;

    /**
     * 总分等级
     */
    @ExcelProperty(value = "总分等级", index = 44)
    private String totalScoreLevel;
}

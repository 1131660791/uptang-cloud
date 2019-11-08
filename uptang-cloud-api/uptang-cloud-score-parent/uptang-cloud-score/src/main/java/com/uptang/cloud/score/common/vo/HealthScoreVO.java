package com.uptang.cloud.score.common.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * @createtime : 2019-11-08 8:55
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HealthScoreVO extends BaseVO<HealthScoreVO> implements Serializable, Cloneable {

    /**
     * 年级编号
     */
    @ApiModelProperty(notes = "年级编号")
    @ExcelProperty(value = "班级编号", index = 1)
    private String gradeCode;

    /**
     * 班级编号
     */
    @ApiModelProperty(notes = "班级编号")
    @ExcelProperty(value = "班级编号", index = 2)
    private Integer classCode;

    /**
     * 班级名称
     */
    @ApiModelProperty(notes = "班级名称")
    @ExcelProperty(value = "班级名称", index = 3)
    private String gradeName;

    /**
     * 学籍号
     */
    @ApiModelProperty(notes = "学籍号")
    @ExcelProperty(value = "学籍号", index = 4)
    private String studentCode;

    /**
     * 姓名
     */
    @ApiModelProperty(notes = "姓名")
    @ExcelProperty(value = "姓名")
    private String studentName;

    /**
     * 性别
     */
    @ApiModelProperty(notes = "性别")
    @ExcelProperty(value = "性别")
    private String gender;

    /**
     * 出生日期
     */
    @ApiModelProperty(notes = "出生日期")
    @ExcelProperty(value = "出生日期")
    private Date birth;

    /**
     * 家庭住址
     */
    @ApiModelProperty(notes = "家庭住址")
    @ExcelProperty(value = "家庭住址")
    private String homeAddress;

    /**
     * 身高
     */
    @ApiModelProperty(notes = "身高")
    @ExcelProperty(value = "身高")
    private Double height;

    /**
     * 体重
     */
    @ApiModelProperty(notes = "体重")
    @ExcelProperty(value = "体重")
    private Double bodyWeight;

    /**
     * 体重评分
     */
    @ApiModelProperty(notes = "体重评分")
    @ExcelProperty(value = "体重评分")
    private Double bodyWeightScore;

    /**
     * 体重等级
     */
    @ApiModelProperty(notes = "体重等级")
    @ExcelProperty(value = "体重等级")
    private String bodyWeightLevel;

    /**
     * 肺活量
     */
    @ApiModelProperty(notes = "肺活量")
    @ExcelProperty(value = "肺活量")
    private Double vitalCapacity;

    /**
     * 肺活量评分
     */
    @ApiModelProperty(notes = "肺活量评分")
    @ExcelProperty(value = "肺活量评分")
    private Double vitalCapacityScore;

    /**
     * 肺活量等级
     */
    @ApiModelProperty(notes = "肺活量等级")
    @ExcelProperty(value = "肺活量等级")
    private String vitalCapacityLevel;

    /**
     * 50米跑
     */
    @ApiModelProperty(notes = "50米跑")
    @ExcelProperty(value = "50米跑")
    private Double fiftyMetersRun;

    /**
     * 50米跑评分
     */
    @ApiModelProperty(notes = "50米跑评分")
    @ExcelProperty(value = "50米跑评分")
    private Double fiftyMetersScore;

    /**
     * 50米跑等级
     */
    @ApiModelProperty(notes = "50米跑等级")
    @ExcelProperty(value = "50米跑等级")
    private String fiftyMetersLevel;

    /**
     * 立定跳远
     */
    @ApiModelProperty(notes = "立定跳远")
    @ExcelProperty(value = "立定跳远")
    private Double standingLongJump;

    /**
     * 立定跳远评分
     */
    @ApiModelProperty(notes = "立定跳远评分")
    @ExcelProperty(value = "立定跳远评分")
    private Double standingLongJumpScore;

    /**
     * 立定跳远等级
     */
    @ApiModelProperty(notes = "立定跳远等级")
    @ExcelProperty(value = "立定跳远等级")
    private String standingLongJumpLevel;

    /**
     * 坐位体前屈
     */
    @ApiModelProperty(notes = "坐位体前屈")
    @ExcelProperty(value = "坐位体前屈")
    private Double flexion;

    /**
     * 坐位体前屈评分
     */
    @ApiModelProperty(notes = "坐位体前屈评分")
    @ExcelProperty(value = "坐位体前屈评分")
    private Double flexionScore;

    /**
     * 坐位体前屈等级
     */
    @ApiModelProperty(notes = "坐位体前屈等级")
    @ExcelProperty(value = "坐位体前屈等级")
    private String flexionLevel;

    /**
     * 800米跑
     */
    @ApiModelProperty(notes = "800米跑")
    @ExcelProperty(value = "800米跑")
    private String eightHundredMeters;

    /**
     * 800米跑评分
     */
    @ApiModelProperty(notes = "800米跑评分")
    @ExcelProperty(value = "800米跑评分")
    private Double eightHundredMetersScore;

    /**
     * 800米跑等级
     */
    @ApiModelProperty(notes = "800米跑等级")
    @ExcelProperty(value = "800米跑等级")
    private String eightHundredMetersLevel;

    /**
     * 800米跑附加分
     */
    @ApiModelProperty(notes = "800米跑附加分")
    @ExcelProperty(value = "800米跑附加分")
    private Double additionalPoints800;

    /**
     * 1000米跑
     */
    @ApiModelProperty(notes = "1000米跑")
    @ExcelProperty(value = "1000米跑")
    private String oneKilometerMeters;

    /**
     * 1000米跑评分
     */
    @ApiModelProperty(notes = "1000米跑评分")
    @ExcelProperty(value = "1000米跑评分")
    private Double oneKilometerMetersScore;

    /**
     * 1000米跑等级
     */
    @ApiModelProperty(notes = "1000米跑等级")
    @ExcelProperty(value = "1000米跑等级")
    private String oneKilometerMetersLevel;

    /**
     * 1000米跑附加分
     */
    @ApiModelProperty(notes = "1000米跑附加分")
    @ExcelProperty(value = "1000米跑附加分")
    private Double additionalPoints1000;

    /**
     * 一分钟仰卧起坐
     */
    @ApiModelProperty(notes = "一分钟仰卧起坐")
    @ExcelProperty(value = "一分钟仰卧起坐")
    private String sitUp;

    /**
     * 一分钟仰卧起坐评分
     */
    @ApiModelProperty(notes = "一分钟仰卧起坐评分")
    @ExcelProperty(value = "一分钟仰卧起坐评分")
    private Double sitUpScore;

    /**
     * 1000米跑等级
     */
    @ApiModelProperty(notes = "一分钟仰卧起坐等级")
    @ExcelProperty(value = "一分钟仰卧起坐等级")
    private String sitUpLevel;

    /**
     * 一分钟仰卧起坐附加分
     */
    @ApiModelProperty(notes = "一分钟仰卧起坐附加分")
    @ExcelProperty(value = "一分钟仰卧起坐附加分")
    private Double sitUpAdditionalPoints;

    /**
     * 引体向上
     */
    @ApiModelProperty(notes = "引体向上")
    @ExcelProperty(value = "引体向上")
    private String pullUp;

    /**
     * 引体向上评分
     */
    @ApiModelProperty(notes = "引体向上评分")
    @ExcelProperty(value = "引体向上评分")
    private Double pullUpScore;

    /**
     * 引体向上等级
     */
    @ApiModelProperty(notes = "引体向上等级")
    @ExcelProperty(value = "引体向上等级")
    private String pullUpLevel;

    /**
     * 引体向上附加分
     */
    @ApiModelProperty(notes = "引体向上附加分")
    @ExcelProperty(value = "引体向上附加分")
    private Double pullUpAdditionalPoints;

    /**
     * 标准分
     */
    @ApiModelProperty(notes = "标准分")
    @ExcelProperty(value = "标准分")
    private Double standardScore;

    /**
     * 附加分
     */
    @ApiModelProperty(notes = "附加分")
    @ExcelProperty(value = "附加分")
    private Double additionalPoints;

    /**
     * 总分
     */
    @ApiModelProperty(notes = "总分")
    @ExcelProperty(value = "总分")
    private Double totalScore;

    /**
     * 总分等级
     */
    @ApiModelProperty(notes = "总分等级")
    @ExcelProperty(value = "总分等级")
    private Double totalScoreLevel;
}

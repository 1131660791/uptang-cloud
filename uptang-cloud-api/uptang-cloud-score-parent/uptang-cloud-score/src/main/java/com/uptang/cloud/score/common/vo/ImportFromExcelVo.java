package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 11:35
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportFromExcelVo extends BaseVO<ImportFromExcelVo> implements Serializable, Cloneable {

    /**
     * 学校ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long schoolId;

    /**
     * 学校名称
     */
    @ApiModelProperty(notes = "学校名称")
    private String schoolName;

    /**
     * 年级ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long gradeId;

    /**
     * 年级名称
     */
    @ApiModelProperty(notes = "年级名称")
    private String gradeName;

    /**
     * 班级学校ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long classId;

    /**
     * 班级名称
     */
    @ApiModelProperty(notes = "学校名称")
    private String className;

    /**
     * 学期编码
     */
    @ApiModelProperty(notes = "学期编码 0 上半学期 1 下班学期")
    private SemesterEnum semesterCode;

    /**
     * 学期名
     */
    @ApiModelProperty(notes = "学期名")
    private String semesterName;

    /**
     * 成绩类型
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private ScoreTypeEnum scoreType;
}

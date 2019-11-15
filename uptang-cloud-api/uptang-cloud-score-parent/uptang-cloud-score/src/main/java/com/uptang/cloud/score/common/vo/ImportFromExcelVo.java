package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
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
     * 年级ID
     */
    @ApiModelProperty(notes = "年级ID")
    private Long gradeId;

    /**
     * 学期ID
     */
    @ApiModelProperty(notes = "学期ID")
    private Long semesterId;

    /**
     * 班级ID
     */
    @ApiModelProperty(notes = "班级ID")
    private Long classId;

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

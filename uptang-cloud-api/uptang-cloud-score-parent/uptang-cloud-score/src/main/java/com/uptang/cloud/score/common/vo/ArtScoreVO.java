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
 * @createtime : 2019-11-08 9:26
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ArtScoreVO extends BaseVO<ArtScoreVO> implements Serializable, Cloneable {

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
     * 成绩
     */
    @ApiModelProperty(notes = "成绩")
    @ExcelProperty(value = "成绩")
    private Double score;
}


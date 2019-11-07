package com.uptang.cloud.score.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 10:49
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResumeScoreVO implements Serializable {

    @ApiModelProperty(notes = "成绩")
    private ScoreVO score;

    @ApiModelProperty(notes = "履历")
    private AcademicResumeVO resume;

}

package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-14 10:45
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ScoreVO extends BaseVO<ScoreVO> implements Serializable, Cloneable {

    /**
     * 科目成绩
     */
    @ApiModelProperty(notes = "科目ID")
    private List<SubjectVO> subjects;

    /**
     * 履历
     */
    @ApiModelProperty(notes = "科目ID")
    private AcademicResumeVO resume;
}

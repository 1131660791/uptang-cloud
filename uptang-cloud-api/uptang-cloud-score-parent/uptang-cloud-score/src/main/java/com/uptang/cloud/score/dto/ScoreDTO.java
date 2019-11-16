package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import lombok.Data;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-14 10:44
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ScoreDTO {

    /**
     * 科目成绩
     */
    private List<Subject> subjects;

    /**
     * 履历
     */
    private AcademicResume resume;

    /**
     * 请求参数信息
     */
    private RequestParameter parameter;
}

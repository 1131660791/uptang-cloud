package com.uptang.cloud.score.common.dto;

import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 11:02
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class Excel implements Serializable {

    /**
     * 科目
     */
    private List<Subject> subjects;

    /**
     * 履历
     */
    private AcademicResume resume;
}
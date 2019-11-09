package com.uptang.cloud.score.dto;

import lombok.Data;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 15:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 年级课程信息
 */
@Data
public class GradeCourseDTO {

    private Long id;
    /**
     * 年级编号
     */
    private Long gradeId;
    /**
     * 学科名称
     */
    private String subjectName;
    /**
     * 总分分值
     */
    private Long totalScore;

    /**
     * 二级指标编号
     */
    private Integer indexId;

    /**
     * 数据状态
     */
    private Integer status;
}

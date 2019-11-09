package com.uptang.cloud.score.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:27
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ArtScoreDTO implements Serializable {

    /**
     * 年级
     */
    private String gradeCode;

    /**
     * 班级
     */
    private Integer classCode;

    /**
     * 学籍号
     */
    private String studentCode;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 成绩
     */
    private Integer score;
}

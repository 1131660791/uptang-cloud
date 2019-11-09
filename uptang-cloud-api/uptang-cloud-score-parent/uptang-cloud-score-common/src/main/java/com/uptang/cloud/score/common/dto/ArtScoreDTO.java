package com.uptang.cloud.score.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "年级", index = 0)
    private String gradeCode;

    /**
     * 班级
     */
    @ExcelProperty(value = "班级", index = 1)
    private Integer classCode;

    /**
     * 学籍号
     */
    @ExcelProperty(value = "学籍号", index = 2)
    private String studentCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 3)
    private String studentName;

    /**
     * 成绩
     */
    @ExcelProperty(value = "成绩", index = 4)
    private Double score;
}

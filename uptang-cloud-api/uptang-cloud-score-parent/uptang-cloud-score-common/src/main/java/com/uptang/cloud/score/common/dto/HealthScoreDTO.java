package com.uptang.cloud.score.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 14:08
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 * 年级编号 班级编号	班级名称	学籍号  民族代码	姓名	 性别  出生日期  家庭住址
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HealthScoreDTO extends Excel {

    /**
     * 年级编号
     */
    @ExcelProperty(value = "年级编号", index = 0)
    private String gradeCode;

    /**
     * 班级编号
     */
    @ExcelProperty(value = "班级编号", index = 1)
    private Integer classCode;

    /**
     * 班级名称
     */
    @ExcelProperty(value = "班级名称", index = 2)
    private String className;

    /**
     * 学籍号
     */
    @ExcelProperty(value = "学籍号", index = 3)
    private String studentCode;

    /**
     * 民族代码
     */
    @ExcelProperty(value = "民族代码", index = 4)
    private Integer nationCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 5)
    private String studentName;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别", index = 6)
    private String gender;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 7)
    private Date birth;

    /**
     * 住址
     */
    @ExcelProperty(value = "住址", index = 8)
    private String address;
}

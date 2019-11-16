package com.uptang.cloud.score.common.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:08
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : 下面四列为必须
 * 年级	    班级	 学籍号	            姓名
 * 初2018级	1	G511402200604020443	余佳怡
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExcelDTO extends Excel implements Serializable {

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
     * 学籍号
     */
    @ExcelProperty(value = "学籍号", index = 2)
    private String studentCode;

    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 3)
    private String studentName;
}

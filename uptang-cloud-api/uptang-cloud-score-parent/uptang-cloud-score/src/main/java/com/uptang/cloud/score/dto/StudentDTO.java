package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 16:31
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
public class StudentDTO {

    private Long id;

    /**
     * student_num	string	R	学籍号
     */
    @JsonProperty("student_num")
    private Long studentCode;


    /**
     * student_name	string	R	学生姓名
     */
    @JsonProperty("student_name")
    private Long studentName;


    /**
     * fk_school_id	int	R	学校编号
     */
    @JsonProperty("fk_school_id")
    private Long schoolId;

    /**
     * fk_grade_id	int	R	年级编号
     */
    @JsonProperty("fk_grade_id")
    private Long gradeId;

    /**
     * status	int	R	数据状态，1为有效
     */
    private Integer status;

    /**
     * province	string	R	省
     */
    private String province;

    /**
     * city	string	R	市
     */
    private String city;

    /**
     * district	string	R	区
     */
    private String district;

    /**
     * school_code	int	R	学校代码
     */
    @JsonProperty("school_code")
    private Long schoolCode;

    /**
     * school_name	string	R	学校名称
     */
    @JsonProperty("school_name")
    private Long schoolName;

    /**
     * grade_name	string	R	年级名称
     */
    @JsonProperty("grade_code")
    private Long gradeCode;

    /**
     * school_code	int	R	学校代码
     */
    @JsonProperty("grade_name")
    private Long gradeName;

    /**
     * class_name	string	R	班级名称
     */
    @JsonProperty("class_name")
    private Long className;

    /**
     * class_code	int	R	班级代码
     */
    @JsonProperty("class_code")
    private Long classCode;


}

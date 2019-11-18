package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uptang.cloud.pojo.enums.GenderEnum;
import lombok.Data;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 16:31
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
public class StudentDTO {

    /**
     */
    private Long id;

    /**
     * student_num	string	R	学籍号
     */
    @JsonProperty("studentNum")
    private String studentCode;

    /**
     * student_name	string	R	学生姓名
     */
    @JsonProperty("studentName")
    private String studentName;

    /**
     * school_code	int	R	学校代码
     */
    @JsonProperty("schoolCode")
    private String schoolCode;

    /**
     * school_name	string	R	学校名称
     */
    @JsonProperty("schoolName")
    private String schoolName;

    /**
     * gradeCode	string	R	年级名称
     */
    @JsonProperty("gradeCode")
    private String gradeCode;

    /**
     * school_code	int	R	学校代码
     */
    @JsonProperty("gradeName")
    private String gradeName;

    /**
     * class_name	string	R	班级名称
     */
    @JsonProperty("className")
    private String className;

    /**
     * class_code	int	R	班级代码
     */
    @JsonProperty("classCode")
    private String classCode;

    /**
     * 学校编号
     */
    private Long schoolId;

    /**
     * 年级编号
     */
    private Long gradeId;

    /**
     * 班级编号
     */
    private Long classId;

    /**
     * 学生唯一编号
     */
    private Long guid;

    /**
     * 性别
     */
    private GenderEnum sex;
}

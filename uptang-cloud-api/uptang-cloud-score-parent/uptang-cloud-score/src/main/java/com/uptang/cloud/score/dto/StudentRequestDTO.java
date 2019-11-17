package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:30
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentRequestDTO extends RestRequestDTO {

    @JsonProperty("student_name")
    private String studentName;

    /**
     * 学籍号
     */
    @JsonProperty("student_num")
    private String studentCode;

    /**
     * 年级编号
     */
    @JsonProperty("fk_grade_id")
    private Long gradeId;

    /**
     * 班级编号
     */
    @JsonProperty("fk_class_id")
    private Long classId;

    /**
     * 学校编号
     */
    @JsonProperty("fk_school_id")
    private Long schoolId;

    /**
     * status	int	O	数据状态, 1为有效
     */
    private Status status;

    @Getter
    @AllArgsConstructor
    public enum Status {
        /**
         * 有效数据
         */
        YES(1),

        /**
         * 无效数据
         */
        NO(0);
        private int code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"studentName\":\"").append(studentName).append('\"');
        sb.append(",\"studentCode\":\"").append(studentCode).append('\"');
        sb.append(",\"gradeId\":").append(gradeId);
        sb.append(",\"classId\":").append(classId);
        sb.append(",\"schoolId\":").append(schoolId);
        sb.append(",\"status\":").append(status);
        sb.append('}');
        return sb.toString();
    }
}

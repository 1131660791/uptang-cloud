package com.uptang.cloud.score.dto;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 16:40
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ArchiveScoreDTO implements Serializable {

    /**
     * 归档表ID
     */
    private Long id;

    private Date createdTime;

    private Date modifiedTime;

    /**
     * 学生性别
     */
    private GenderEnum gender;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 学校名
     */
    private String schoolName;

    /**
     * 学期
     */
    private Long semesterId;

    /**
     * 班级
     */
    private Long classId;

    /**
     * 年级
     */
    private Long gradeId;

    /**
     * 学校
     */
    private Long schoolId;

    /**
     * 履历ID
     */
    private Long resumeId;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 学籍号
     */
    private String studentCode;

    /**
     * 归档数据
     */
    private String detail;


    /**
     * 成绩类型
     */
    private ScoreTypeEnum type;
}

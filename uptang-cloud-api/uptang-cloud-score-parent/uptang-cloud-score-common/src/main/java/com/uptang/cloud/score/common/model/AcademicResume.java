package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuppressWarnings("serial")
@TableName("academic_resume")
public class AcademicResume implements Serializable {

    /**
     * @type bigint(20) unsigned
     * @comment Primary key
     * @IdxName PRI
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * @type bigint(20) unsigned
     * @comment student_id学生ID
     * @Column(name = "student_id")
     */
    private Long studentId;

    /**
     * @type bigint(20) unsigned
     * @comment 学期ID
     * @Column(name = "semester_id")
     */
    private Long semesterId;

    /**
     * @type bigint(20)
     * @comment 学校ID
     * @Column(name = "school_id")
     */
    private Long schoolId;

    /**
     * @type bigint(20) unsigned
     * @comment 年级编号
     * @Column(name = "grade_id")
     */
    private Long gradeId;

    /**
     * @type bigint(20) unsigned
     * @comment 班级编号
     * @Column(name = "class_id")
     */
    private Long classId;

    /**
     * @type varchar(20)
     * @comment 学期名称
     * @Column(name = "semester_name")
     */
    private String semesterName;

    /**
     * @type varchar(100)
     * @comment 学校名称
     * @Column(name = "school_name")
     */
    private String schoolName;

    /**
     * @type varchar(20)
     * @comment 年级名称
     * @Column(name = "grade_name")
     */
    private String gradeName;

    /**
     * @type varchar(20)
     * @comment 班级名称
     * @Column(name = "class_name")
     */
    private String className;

    /**
     * @type varchar(50)
     * @comment 学生姓名
     * @Column(name = "student_name")
     */
    private String studentName;

    /**
     * @type varchar(30)
     * @comment 学籍号
     * @Column(name = "student_code")
     */
    private String studentCode;

    /**
     * @type tinyint(1) unsigned
     * @comment 学生性别 0 未知性别 1 男 2 女 9 未说明性别
     * @Column(name = "gender")
     */
    private GenderEnum gender;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     * @Column(name = "score_type")
     */
    private ScoreTypeEnum scoreType;

    /**
     * @type bigint(20) unsigned
     * @comment 创建人ID
     * @Column(name = "creator_id")
     */
    private Long creatorId;

    /**
     * @type bigint(20) unsigned
     * @comment 修改人
     * @Column(name = "modifier_id")
     */
    private Long modifierId;

    /**
     * @type timestamp
     * @comment 创建时间
     * @Column(name = "created_time")
     */
    private java.util.Date createdTime;

    /**
     * @type timestamp
     * @comment 修改时间
     * @Column(name = "modified_time")
     */
    private java.util.Date modifiedTime;
}


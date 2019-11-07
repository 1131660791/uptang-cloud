package com.uptang.cloud.score.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
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
     * @type varchar(100)
     * @comment 学校名称
     */
    private String school;

    /**
     * @type bigint(20)
     * @comment 学校ID
     */
    private Long schoolId;

    /**
     * @type bigint(20) unsigned
     * @comment 年级编号
     */
    private Long gradeId;

    /**
     * @type varchar(20)
     * @comment 年级名称
     */
    private String gradeName;

    /**
     * @type bigint(20) unsigned
     * @comment 班级编号
     */
    private Long classId;

    /**
     * @type varchar(20)
     * @comment 班级名称
     */
    private String className;

    /**
     * @type tinyint(1) unsigned
     * @comment 学期 0 上半学期 1 下班学期
     */
    private SemesterEnum semesterCode;

    /**
     * @type varchar(20)
     * @comment 学期名称
     */
    private String semesterName;

    /**
     * @type varchar(50)
     * @comment 学生姓名
     */
    private String studentName;

    /**
     * @type varchar(30)
     * @comment 学籍号 E.g: G511402200509048827
     */
    private String studentCode;

    /**
     * @type tinyint(1) unsigned
     * @comment 学生性别 0 为止性别 1 男 2 女 9 未说明性别
     */
    private GenderEnum gender;

    /**
     * @type bigint(20) unsigned
     * @comment 成绩表ID
     */
    private Long scoreId;

    /**
     * @type tinyint(1) unsigned
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    private ScoreTypeEnum scoreType;

    /**
     * @type bigint(20) unsigned
     * @comment 创建人ID
     */
    private Long createdFounderId;

    /**
     * @type bigint(20) unsigned
     * @comment 修改人ID
     */
    private Long updatedFounderId;

    /**
     * @type timestamp
     * @comment 创建时间
     */
    private java.util.Date createdTime;

    /**
     * @type timestamp
     * @comment 修改时间
     */
    private java.util.Date updatedTime;

    public AcademicResume(Long id) {
        this.id = id;
    }
}


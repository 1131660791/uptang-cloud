package com.uptang.cloud.score.dto;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 19:59
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ShowScoreDTO implements Serializable {

    private List<Subject> subjects;

    /**
     * ResumeID
     */
    private Long id;

    /**
     * 学生ID guid
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学籍号
     */
    private String studentCode;

    /**
     * 年级ID
     */
    private Long gradeId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学期ID
     */
    private Long semesterId;

    /**
     * 学期名称
     */
    private String semesterName;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 成绩状态 公示 1 公示中 2 归档 3 已提交
     */
    private ScoreStatusEnum state;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 成绩类型
     */
    private ScoreTypeEnum scoreType;

    /**
     * 公示开始时间[只有在”公示中“才会有开始时间]
     */
    private Date startedTime;

    /**
     * 公示结束时间[只有在”归档“才会有结束时间]
     */
    private Date finishTime;

    private Long creatorId;

    private Date createdTime;

    private Date modifiedTime;
}

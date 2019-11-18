package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 20:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ShowScoreVO extends BaseVO<ShowScoreVO> implements Serializable, Cloneable {

    /**
     * 科目
     */
    private List<SubjectVO> subjects;

    /**
     * ResumeID
     */
    private Long id;

    /**
     * 学生ID guid
     */
    private Long studentId;

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

    /**
     * 学期名称
     */
    private String semesterName;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学籍号
     */
    private String studentCode;
    private Long creatorId;
    private Date createdTime;
    private Date modifiedTime;
}

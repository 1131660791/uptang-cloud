package com.uptang.cloud.score.common.model;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 16:45
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ObjectionRecordResume implements Serializable {

    private Long id;
    private Long subjectId;
    private Long resumeId;

    private Long studentId;
    private Long reviewId;
    private Long creatorId;
    private Long semesterId;
    private Long schoolId;
    private Long gradeId;
    private Long classId;

    private ScoreTypeEnum scoreType;
    private GenderEnum gender;
    private String semesterName;
    private String schoolName;
    private String gradeName;
    private String studentName;
    private String studentCode;
    private String reviewDesc;
    private String description;
    private String subjectName;
    private String scoreText;
    private String className;

    private Integer state;
    private ReviewEnum reviewStat;
    private Integer subjectCode;
    private Integer scoreNumber;

    private Date createdTime;
    private Date modifiedTime;
}

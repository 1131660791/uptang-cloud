package com.uptang.cloud.score.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 17:25
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObjectionRecordResumeVO extends BaseVO<ObjectionRecordResumeVO> implements Serializable, Cloneable {
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

    private ObjectionEnum state;
    private ReviewEnum reviewStat;
    private Integer subjectCode;
    private Double scoreNumber;

    private Date createdTime;
    private Date modifiedTime;
}

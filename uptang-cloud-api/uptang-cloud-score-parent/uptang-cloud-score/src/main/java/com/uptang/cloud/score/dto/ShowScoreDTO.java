package com.uptang.cloud.score.dto;

import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 19:59
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class ShowScoreDTO implements Serializable {

    private ScoreTypeEnum scoreType;
    private Long subjectId;
    private Long studentId;
    private Long gradeId;
    private Long classId;
    private Long schoolId;
    private Long semesterId;
    private Long resumeId;

    private String subjectName;
    private String scoreText;
    private String gradeName;
    private String className;
    private String schoolName;

    private Integer subjectCode;
    private Integer scoreNumber;
    private ScoreStatusEnum state;
    private GenderEnum gender;

    private Date startedTime;
    private Date finishTime;

}

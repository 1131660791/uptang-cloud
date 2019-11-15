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
    private ScoreTypeEnum scoreType;
    private Long subjectId;
    private Long studentId;
    private Long gradeId;
    private Long classId;
    private Long schoolId;
    private Long semesterId;

    private String subjectName;
    private String scoreText;
    private String gradeName;
    private String className;
    private String schoolName;

    private Integer subjectCode;
    private Double scoreNumber;
    private ScoreStatusEnum state;
    private GenderEnum gender;

    private Date startedTime;
    private Date finishTime;
}

package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import lombok.Data;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 11:41
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
public class ImportFromExcelDTO {

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 年级ID
     */
    private Long gradeId;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 班级学校ID
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学期编码
     */
    private SemesterEnum semesterCode;

    /**
     * 学期名
     */
    private String semesterName;

    /**
     * 成绩类型
     */
    private ScoreTypeEnum scoreType;

    private String token;

    private Long userId;
}

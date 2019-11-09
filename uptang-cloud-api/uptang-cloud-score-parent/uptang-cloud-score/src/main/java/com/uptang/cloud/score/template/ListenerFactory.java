package com.uptang.cloud.score.template;

import com.alibaba.excel.event.AnalysisEventListener;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:18
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public class ListenerFactory {

    /**
     * @param type         Excel数据类型
     * @param userId       当前用户ID
     * @param type         成绩类型
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     */
    public static AnalysisEventListener newListener(ScoreTypeEnum type,
                                                    Long userId,
                                                    Long gradeId,
                                                    Long classId,
                                                    Long schoolId,
                                                    SemesterEnum semesterCode) {
        switch (type) {
            case HEALTH:
                return new HealthAnalysisEventListener
                        (userId, gradeId, classId, schoolId, semesterCode);
            case ART:
                return new ArtAnalysisEventListener
                        (userId, gradeId, classId, schoolId, semesterCode);
            default:
                return new AcademicAnalysisEventListener
                        (userId, gradeId, classId, schoolId, semesterCode);
        }
    }
}

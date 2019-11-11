package com.uptang.cloud.score.template;

import com.alibaba.excel.event.AnalysisEventListener;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;

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
     * @param excel
     */
    public static AnalysisEventListener newListener(ImportFromExcelDTO excel) {
        switch (excel.getScoreType()) {
            case HEALTH:
                return new HealthAnalysisEventListener(excel);
            case ART:
                return new ArtAnalysisEventListener(excel);
            default:
                return new AcademicAnalysisEventListener(excel);
        }
    }
}

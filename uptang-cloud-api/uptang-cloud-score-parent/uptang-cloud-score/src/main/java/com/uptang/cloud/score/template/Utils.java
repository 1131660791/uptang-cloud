package com.uptang.cloud.score.template;

import com.uptang.cloud.score.common.model.AcademicResume;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.uptang.cloud.score.template.AbstractAnalysisEventListener.SPIN_OVER_TIME;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 10:30
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
class Utils {

    /**
     * 设置履历表SubjectIds
     *
     * @param subjectIds
     * @param academicResumes
     */
    public static void setSubjectIds(Map<String, List<Long>> subjectIds,
                                     List<AcademicResume> academicResumes) {
        Set<Map.Entry<String, List<Long>>> entries = subjectIds.entrySet();
        // 科目
        for (Map.Entry<String, List<Long>> entry : entries) {
            // 履历
            for (AcademicResume resume : academicResumes) {
                // 插入科目IDs
                if (resume.getStudentCode().equals(entry.getKey())) {
                    resume.setSubjectIds(entry.getValue().toString());
                }
            }
        }
    }

    /**
     * 自旋
     *
     * @param subjectIds
     * @param academicResumes
     */
    public static void spin(Map<String, List<Long>> subjectIds,
                            List<AcademicResume> academicResumes) {
        final long spinStartTime = System.currentTimeMillis();
        while (((System.currentTimeMillis() - spinStartTime) < SPIN_OVER_TIME)
                && (academicResumes.size() != subjectIds.size())) {
            Utils.setSubjectIds(subjectIds, academicResumes);
        }
    }
}

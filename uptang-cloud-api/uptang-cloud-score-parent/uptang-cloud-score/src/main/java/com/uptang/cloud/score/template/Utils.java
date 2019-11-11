package com.uptang.cloud.score.template;

import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;

import java.util.Date;
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

    /**
     * 从前端传入参数
     *
     * @param excel
     * @return
     */
    public static AcademicResume formClientRequestParam(ImportFromExcelDTO excel) {
        AcademicResume resume = new AcademicResume();

        // 创建时间
        resume.setCreatedTime(new Date());

        // 创建人
        resume.setCreatedFounderId(excel.getUserId());

        // 学期编码 学期名称
        resume.setSemesterCode(excel.getSemesterCode());
        resume.setSemesterName(excel.getSemesterName());

        // 学校 年级 班级 ID
        resume.setSchoolId(excel.getSchoolId());
        resume.setGradeId(excel.getGradeId());
        resume.setClassId(excel.getClassId());

        // 学校 年级 班级 名称
        resume.setSchool(excel.getSchoolName());
        resume.setGradeName(excel.getGradeName());
        resume.setClassName(excel.getClassName());
        return resume;
    }
}

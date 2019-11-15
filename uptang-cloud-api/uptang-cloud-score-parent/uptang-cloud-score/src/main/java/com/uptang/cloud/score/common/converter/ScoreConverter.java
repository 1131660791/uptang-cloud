package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.vo.ScoreVO;
import com.uptang.cloud.score.common.vo.SubjectVO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.ScoreDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-14 10:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public interface ScoreConverter {

    /**
     * VO 2 model
     *
     * @param score
     * @return
     */
    static ScoreDto toModel(ScoreVO score, RequestParameter parameter) {
        if (score != null) {
            ScoreDto scoreDTO = new ScoreDto();
            AcademicResume resume = AcademicResumeConverter.INSTANCE.toModel(score.getResume());
            parameter.setSchoolId(resume.getSchoolId());
            parameter.setGradeId(resume.getGradeId());
            parameter.setClassId(resume.getClassId());
            parameter.setSemesterId(resume.getSemesterId());
            scoreDTO.setResume(resume);
            scoreDTO.setParameter(parameter);
            List<SubjectVO> subjects = score.getSubjects();
            if (subjects != null && subjects.size() > 0) {
                List<Subject> subjects1 = new ArrayList<>(subjects.size());
                subjects.forEach(subjectVO -> subjects1.add(SubjectConverter.INSTANCE.toModel(subjectVO)));
                scoreDTO.setSubjects(subjects1);
            }
            return scoreDTO;
        }
        return null;
    }
}

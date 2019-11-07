package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import com.uptang.cloud.score.repository.ScoreRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreRepository, Score> implements IScoreService {

    private final IAcademicResumeService academicResumeService;

    public ScoreServiceImpl(IAcademicResumeService academicResumeService) {
        this.academicResumeService = academicResumeService;
    }

    @Override
    public boolean save(ResumeJoinScoreDTO resumeJoinScore) {
        Score score = new Score();
        score.setType(resumeJoinScore.getScoreType());
        score.setScoreNumber(resumeJoinScore.getScoreNumber());
        score.setSubject(resumeJoinScore.getSubject());
        score.setScoreText(resumeJoinScore.getScoreText());
        getBaseMapper().save(score);

        if (score.getId() != null) {
            AcademicResume resume = new AcademicResume();
            resume.setCreatedFounderId(resumeJoinScore.getCreatedFounderId());
            resume.setScoreId(resumeJoinScore.getScoreId());
            resume.setClassId(resumeJoinScore.getClassId());
            resume.setClassName(resumeJoinScore.getClassName());
            resume.setGender(resumeJoinScore.getGender());
            resume.setStudentName(resumeJoinScore.getStudentName());
            resume.setSchool(resumeJoinScore.getSchool());
            resume.setSchoolId(resumeJoinScore.getSchoolId());
            resume.setGradeId(resumeJoinScore.getGradeId());
            resume.setGradeName(resumeJoinScore.getGradeName());
            resume.setScoreType(resumeJoinScore.getScoreType());
            resume.setCreatedTime(new Date());
            resume.setScoreId(score.getId());
            return academicResumeService.save(resume);
        }
        return false;
    }
}


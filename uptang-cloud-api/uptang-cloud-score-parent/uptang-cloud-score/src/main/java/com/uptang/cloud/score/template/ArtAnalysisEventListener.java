package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.dto.ArtScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.score.common.enums.SubjectEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 20:52
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class ArtAnalysisEventListener extends AbstractAnalysisEventListener<ArtScoreDTO> {

    private final List<AcademicResume> academicResumes = new ArrayList<>();

    private final List<Score> scores = new ArrayList<>();

    private final List<List<Long>> scoreIds = new ArrayList<>();

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final IScoreService scoreService =
            ApplicationContextHolder.getBean(IScoreService.class);

    public ArtAnalysisEventListener(Long userId, Long gradeId, Long classId, Long schoolId, SemesterEnum semesterCode) {
        super(userId, gradeId, classId, schoolId, semesterCode);
    }

    @Override
    public void doInvoke(ArtScoreDTO artScore, AnalysisContext context) {
        Score score = new Score();
        score.setType(ScoreTypeEnum.ART);
        score.setSubject(SubjectEnum.ART_SUBJECT);
        score.setScoreNumber(Calculator.x10(artScore.getScore()));
        scores.add(score);

        AcademicResume resume = new AcademicResume();
        resume.setCreatedTime(new Date());
        resume.setCreatedFounderId(getUserId());
        resume.setGender(GenderEnum.UNSPECIFIED);
        resume.setScoreType(ScoreTypeEnum.ART);
        resume.setStudentName(artScore.getStudentName());
        resume.setSemesterCode(getSemesterCode());
        resume.setSemesterName(getSemesterCode().getDesc());
        resume.setStudentCode(artScore.getStudentCode());
        resume.setSchoolId(getSchoolId());
        resume.setGradeId(getGradeId());
        resume.setClassId(getClassId());
        //resume.setSchool();
        resume.setGradeName(artScore.getGradeCode());
        //resume.setClassName();
        academicResumes.add(resume);
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.ART);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("用户{}导入{}学校{}年级{}班{}学期的艺术成绩 共计{}名学生",
                    getUserId(), getSchoolId(), getGradeId(), getClassId(), getSemesterCode(), academicResumes.size());
        }

        {
            try {
                Map<Integer, List<Score>> scoreGroupList = getGroupList(scores);
                final Set<Map.Entry<Integer, List<Score>>> entries = scoreGroupList.entrySet();
                for (Map.Entry<Integer, List<Score>> entry : entries) {
                    scoreIds.add(scoreService.batchInsert(entry.getValue()));
                }
            } finally {
                scores.clear();
            }
        }


        {
            List<Long> ids = new ArrayList<>();
            scoreIds.stream().forEach(ids::addAll);
            scoreIds.clear();
            ids.stream().forEach(id -> academicResumes.stream().forEach(resume -> resume.setScoreId(id)));
            ids.clear();
        }

        try {
            Map<Integer, List<AcademicResume>> groupList = getGroupList(academicResumes);
            resumeService.batchSave(groupList);
        } finally {
            academicResumes.clear();
        }
    }
}

package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.core.exception.BusinessException;
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
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 20:52
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class ArtAnalysisEventListener extends AbstractAnalysisEventListener<ArtScoreDTO> {

    private final List<AcademicResume> academicResumes = new ArrayList<>();

    private final Map<String, Long> scoreIds = new ConcurrentHashMap<>();

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final IScoreService scoreService =
            ApplicationContextHolder.getBean(IScoreService.class);

    private final ThreadPoolTaskExecutor subjectExecutor =
            ApplicationContextHolder.getBean("subjectExecutor");

    public ArtAnalysisEventListener(Long userId, Long gradeId, Long classId, Long schoolId, SemesterEnum semesterCode) {
        super(userId, gradeId, classId, schoolId, semesterCode);
    }

    @Override
    public void doInvoke(ArtScoreDTO artScore, AnalysisContext context) {
        // single insert
        subjectExecutor.execute(() -> {
            Score score = new Score();
            score.setType(ScoreTypeEnum.ART);
            score.setScoreText(Strings.EMPTY);
            score.setSubject(SubjectEnum.ART_SUBJECT);
            score.setScoreNumber(Calculator.x10(artScore.getScore()));
            scoreIds.put(artScore.getStudentCode(), scoreService.insert(score));
        });

        AcademicResume resume = new AcademicResume();
        resume.setCreatedTime(new Date());
        resume.setCreatedFounderId(getUserId());
        resume.setScoreType(ScoreTypeEnum.ART);
        resume.setStudentName(artScore.getStudentName());
        resume.setSemesterCode(getSemesterCode());
        resume.setSemesterName(getSemesterCode().getDesc());
        resume.setStudentCode(artScore.getStudentCode());
        resume.setGradeName(artScore.getGradeCode());
        resume.setSchoolId(getSchoolId());
        resume.setGradeId(getGradeId());
        resume.setSubjectIds(Strings.EMPTY);
        resume.setClassId(getClassId());

        resume.setGender(GenderEnum.UNSPECIFIED);
        resume.setSchool("ART");
        resume.setClassName("ART");
        academicResumes.add(resume);
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.ART);
    }

    /**
     * <pre>
     * {
     *     // 批量插入艺术成绩
     *     try {
     *         Map<Integer, List<Score>> scoreGroupList = getGroupList(scores.values());
     *         final Set<Map.Entry<Integer, List<Score>>> entries = scoreGroupList.entrySet();
     *         for (Map.Entry<Integer, List<Score>> entry : entries) {
     *             scoreIds.add(scoreService.batchInsert(entry.getValue()));
     *         }
     *     } finally {
     *         scores.clear();
     *     }
     * }
     *
     * {
     *     List<Long> ids = new ArrayList<>();
     *     scoreIds.stream().forEach(ids::addAll);
     *     scoreIds.clear();
     *     ids.stream().forEach(id -> academicResumes.stream().forEach(resume -> resume.setScoreId(id)));
     *     ids.clear();
     * }
     * <p/>
     * <pre/>
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("用户{}导入{}学校{}年级{}班{}学期的艺术成绩 共计{}名学生",
                    getUserId(), getSchoolId(), getGradeId(), getClassId(), getSemesterCode(), academicResumes.size());
        }

        // 等待成绩存储完成
        final long spinStartTime = System.currentTimeMillis();
        while (((System.currentTimeMillis() - spinStartTime) < SPIN_OVER_TIME)
                && (academicResumes.size() != scoreIds.size())) {
            setScoreId();
        }

        try {
            setScoreId();
            Map<Integer, List<AcademicResume>> groupList = getGroupList(academicResumes);
            resumeService.batchSave(groupList);
        } catch (Exception e) {
            List<Long> collect = scoreIds.entrySet().stream()
                    .map(entry -> entry.getValue())
                    .collect(Collectors.toList());

            scoreService.rollback(getGroupList(collect), ScoreTypeEnum.ART);
            log.error("批量录入学业成绩异常", e);
            throw new BusinessException(e);
        } finally {
            scoreIds.clear();
            academicResumes.clear();
        }
    }

    private void setScoreId() {
        scoreIds.forEach((stuCode, id) -> academicResumes.forEach(resume -> {
            if (resume.getStudentCode().equals(stuCode) && resume.getScoreId() == null) {
                resume.setScoreId(id);
            }
        }));
    }
}

package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SubjectEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:20
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class HealthAnalysisEventListener extends AbstractAnalysisEventListener<HealthScoreDTO> {

    /**
     * ”缓存“ Subject ids {key:学籍号,Value: ids}
     */
    private final Map<String, List<Long>> subjectIds = new ConcurrentHashMap<>();

    private final List<AcademicResume> academicResumes = new ArrayList<>();

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final IScoreService scoreService =
            ApplicationContextHolder.getBean(IScoreService.class);

    private final ThreadPoolTaskExecutor subjectExecutor =
            ApplicationContextHolder.getBean("subjectExecutor");

    public HealthAnalysisEventListener(ImportFromExcelDTO excel) {
        super(excel);
    }

    /**
     * @param health
     * @param context
     */
    @Override
    public void doInvoke(HealthScoreDTO health, AnalysisContext context) {
        academicResumes.add(buildAcademicResume(health));
        subjectExecutor.execute(() -> {
            final List<Score> scoreList = new ArrayList<>(36);
            scoreList.add(SubjectEnum.HEIGHT.toScore(health));
            scoreList.add(SubjectEnum.FIFTY_METERS_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.BODY_WEIGHT_SCORE.toScore(health));
            scoreList.add(SubjectEnum.BODY_WEIGHT_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.VITAL_CAPACITY.toScore(health));
            scoreList.add(SubjectEnum.VITAL_CAPACITY_SCORE.toScore(health));
            scoreList.add(SubjectEnum.VITAL_CAPACITY_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.FIFTY_METERS_RUN.toScore(health));
            scoreList.add(SubjectEnum.BODY_WEIGHT.toScore(health));
            scoreList.add(SubjectEnum.FIFTY_METERS_SCORE.toScore(health));
            scoreList.add(SubjectEnum.STANDING_LONG_JUMP.toScore(health));
            scoreList.add(SubjectEnum.STANDING_LONG_JUMPS_CORE.toScore(health));
            scoreList.add(SubjectEnum.STANDING_LONG_JUMP_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.FLEXION.toScore(health));
            scoreList.add(SubjectEnum.FLEXION_SCORE.toScore(health));
            scoreList.add(SubjectEnum.FLEXION_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.EIGHT_HUNDRED_METERS.toScore(health));
            scoreList.add(SubjectEnum.EIGHT_HUNDRED_METERS_SCORE.toScore(health));
            scoreList.add(SubjectEnum.EIGHT_HUNDRED_METERS_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.ADDITIONAL_POINTS_800.toScore(health));
            scoreList.add(SubjectEnum.ONE_KILO_METERS.toScore(health));
            scoreList.add(SubjectEnum.ONE_KILOMETER_METERS_SCORE.toScore(health));
            scoreList.add(SubjectEnum.ONE_KILOMETER_METERS_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.ADDITIONAL_POINTS_1000.toScore(health));
            scoreList.add(SubjectEnum.SIT_UP.toScore(health));
            scoreList.add(SubjectEnum.SIT_UP_SCORE.toScore(health));
            scoreList.add(SubjectEnum.SIT_UP_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.SIT_UP_ADDITIONAL_POINTS.toScore(health));
            scoreList.add(SubjectEnum.PULL_UP.toScore(health));
            scoreList.add(SubjectEnum.PULL_UP_SCORE.toScore(health));
            scoreList.add(SubjectEnum.PULL_UP_LEVEL.toScore(health));
            scoreList.add(SubjectEnum.PULL_UP_ADDITIONAL_POINTS.toScore(health));
            scoreList.add(SubjectEnum.STANDARD_SCORE.toScore(health));
            scoreList.add(SubjectEnum.ADDITIONAL_POINTS.toScore(health));
            scoreList.add(SubjectEnum.TOTAL_SCORE.toScore(health));
            scoreList.add(SubjectEnum.TOTAL_SCORE_LEVEL.toScore(health));
            subjectIds.put(health.getStudentCode(), scoreService.batchInsert(scoreList));
        });
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.HEALTH);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Utils.spin(subjectIds, academicResumes);

        try {
            Utils.setSubjectIds(subjectIds, academicResumes);
            resumeService.batchSave(getGroupList(academicResumes));
        } catch (Exception e) {
            scoreService.rollback(subjectIds, ScoreTypeEnum.HEALTH, null, null);
            log.error("批量录入体质健康成绩异常", e);
            throw new BusinessException(e);
        } finally {
            subjectIds.clear();
            academicResumes.clear();
        }
    }

    private AcademicResume buildAcademicResume(HealthScoreDTO health) {
        AcademicResume resume = Utils.formClientRequestParam(getExcel());
        resume.setGender(GenderEnum.parse(health.getGender()));
        resume.setScoreType(ScoreTypeEnum.HEALTH);
        resume.setStudentName(health.getStudentName());
        resume.setStudentCode(health.getStudentCode());
        resume.setScoreId(0L);
        return resume;
    }
}

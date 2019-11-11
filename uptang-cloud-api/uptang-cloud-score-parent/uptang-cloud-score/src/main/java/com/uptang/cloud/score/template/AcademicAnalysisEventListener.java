package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.fastjson.JSON;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.pojo.enums.GenderEnum;
import com.uptang.cloud.score.common.dto.AcademicScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.score.common.enums.SubjectEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import com.uptang.cloud.score.util.CacheKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
public class AcademicAnalysisEventListener extends AbstractAnalysisEventListener<AcademicScoreDTO> {

    private final List<AcademicResume> academicResumes = new ArrayList<>();

    /**
     * ”缓存“ Subject ids {key:学籍号,Value: ids}
     */
    private final Map<String, List<Long>> subjectIds = new ConcurrentHashMap<>();

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final IScoreService scoreService =
            ApplicationContextHolder.getBean(IScoreService.class);

    private final ThreadPoolTaskExecutor subjectExecutor =
            ApplicationContextHolder.getBean("subjectExecutor");

    private final StringRedisTemplate redisTemplate =
            ApplicationContextHolder.getBean(StringRedisTemplate.class);

    public AcademicAnalysisEventListener(Long userId, Long gradeId,
                                         Long classId, Long schoolId, SemesterEnum semesterCode) {
        super(userId, gradeId, classId, schoolId, semesterCode);
    }

    @Override
    public void doInvoke(AcademicScoreDTO academicScore, AnalysisContext context) {
        subjectExecutor.execute(() -> {
            final List<Score> scoreList = buildSubjects(academicScore);
            subjectIds.put(academicScore.getStudentCode(), scoreService.batchInsert(scoreList));
        });

        AcademicResume resume = new AcademicResume();
        resume.setCreatedTime(new Date());
        resume.setCreatedFounderId(getUserId());
        resume.setGender(GenderEnum.parse(academicScore.getArt()));
        resume.setScoreType(ScoreTypeEnum.ACADEMIC);
        resume.setStudentName(academicScore.getStudentName());
        resume.setSemesterCode(getSemesterCode());
        resume.setSemesterName(getSemesterCode().getDesc());
        resume.setStudentCode(academicScore.getStudentCode());
        resume.setSchoolId(getSchoolId());
        resume.setGradeId(getGradeId());
        resume.setClassId(getClassId());
        resume.setScoreId(0L);
        resume.setSchool("CODE");
        resume.setGradeName("CODE");
        resume.setClassName("CODE");
        academicResumes.add(resume);
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.ACADEMIC);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("用户{}导入{}学校{}年级{}班{}学期的学业成绩 共计{}名学生",
                    getUserId(), getSchoolId(), getGradeId(), getClassId(), getSemesterCode(), academicResumes.size());
        }

        // 等待科目存储完成
        Utils.spin(subjectIds, academicResumes);

        // cache subject ids
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String cacheKey = CacheKeys.subjectIdsCacheKey(getSchoolId(), getGradeId(), ScoreTypeEnum.ACADEMIC.getCode(), getUserId());
        String hashKey = String.join(":", getUserId() + "", Instant.now().toString());
        hashOperations.put(cacheKey, hashKey, JSON.toJSONString(subjectIds));

        try {
            Utils.setSubjectIds(subjectIds, academicResumes);
            Map<Integer, List<AcademicResume>> groupList = getGroupList(academicResumes);
            resumeService.batchSave(groupList);
        } catch (Exception e) {
            scoreService.rollback(subjectIds, ScoreTypeEnum.ACADEMIC, cacheKey, hashKey);
            log.error("批量录入学业成绩异常", e);
            throw new BusinessException(e);
        } finally {
            subjectIds.clear();
            academicResumes.clear();
            hashOperations.delete(cacheKey, hashKey);
        }
    }

    private List<Score> buildSubjects(AcademicScoreDTO academicScore) {
        final List<Score> scoreList = new ArrayList<>(18);
        scoreList.add(SubjectEnum.MORALITY_LAW.toScore(academicScore));
        scoreList.add(SubjectEnum.CHINESE.toScore(academicScore));
        scoreList.add(SubjectEnum.MATH.toScore(academicScore));
        scoreList.add(SubjectEnum.ENGLISH.toScore(academicScore));
        scoreList.add(SubjectEnum.PHYSICS.toScore(academicScore));
        scoreList.add(SubjectEnum.CHEMISTRY.toScore(academicScore));
        scoreList.add(SubjectEnum.HISTORY.toScore(academicScore));
        scoreList.add(SubjectEnum.GEOGRAPHY.toScore(academicScore));
        scoreList.add(SubjectEnum.BIOLOGICAL.toScore(academicScore));
        scoreList.add(SubjectEnum.PHYSICAL_CULTURE.toScore(academicScore));
        scoreList.add(SubjectEnum.INFORMATION_TECHNOLOGY.toScore(academicScore));
        scoreList.add(SubjectEnum.MUSIC.toScore(academicScore));
        scoreList.add(SubjectEnum.ART.toScore(academicScore));
        scoreList.add(SubjectEnum.PHYSICAL_EXPERIMENT.toScore(academicScore));
        scoreList.add(SubjectEnum.CHEMISTRY_EXPERIMENT.toScore(academicScore));
        scoreList.add(SubjectEnum.BIOLOGICAL_EXPERIMENT.toScore(academicScore));
        scoreList.add(SubjectEnum.LABOR_TECHNICAL_EDUCATION.toScore(academicScore));
        scoreList.add(SubjectEnum.LOCAL_CURRICULUM.toScore(academicScore));
        return scoreList;
    }
}

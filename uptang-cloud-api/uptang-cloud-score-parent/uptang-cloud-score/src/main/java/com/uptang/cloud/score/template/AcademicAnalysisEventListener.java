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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:20
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class AcademicAnalysisEventListener extends AbstractAnalysisEventListener<AcademicScoreDTO> {

    private final List<AcademicResume> academicResumes = new CopyOnWriteArrayList<>();

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

    /**
     * 自旋时间
     */
    public static final long SPIN_OVER_TIME = 30 * 1000;


    @Override
    public void doInvoke(AcademicScoreDTO academicScore, AnalysisContext context) {
        academicResumes.add(buildAcademicResume(academicScore));
        subjectExecutor.execute(() -> {
            final List<Score> scoreList = buildSubjects(academicScore);
            subjectIds.put(academicScore.getStudentCode(), scoreService.batchInsert(scoreList));
        });
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
        final long spinStartTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - spinStartTime < SPIN_OVER_TIME)
                && (academicResumes.size() != subjectIds.size())) {
            setSubjectIds();
        }

        // cache subject ids
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String cacheKey = CacheKeys.subjectIdsCacheKey(getSchoolId(), getGradeId(), ScoreTypeEnum.ACADEMIC.getCode(), getUserId());
        String hashKey = String.join(":", getUserId() + "", Instant.now().toString());
        hashOperations.put(cacheKey, hashKey, JSON.toJSONString(subjectIds));

        try {
            Map<Integer, List<AcademicResume>> groupList = getGroupList(academicResumes);
            resumeService.batchSave(groupList);
            throw new BusinessException("批量录入学生成绩异常");
        } catch (Exception e) {
            log.error("批量录入学业成绩异常", e);
            scoreService.rollback(subjectIds, ScoreTypeEnum.ACADEMIC, cacheKey, hashKey);
            throw new BusinessException(e);
        } finally {
            subjectIds.clear();
            academicResumes.clear();
            hashOperations.delete(cacheKey, hashKey);
        }
    }

    /**
     * 设置履历表SubjectIds
     */
    private void setSubjectIds() {
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

    private AcademicResume buildAcademicResume(AcademicScoreDTO academicScore) {
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
        return resume;
    }
}

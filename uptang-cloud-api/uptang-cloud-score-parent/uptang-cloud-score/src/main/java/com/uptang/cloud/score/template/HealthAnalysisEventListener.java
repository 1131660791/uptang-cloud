package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.handler.PrimitiveResolver;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.service.ISubjectService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.HEALTH;
import static com.uptang.cloud.score.common.util.Calculator.defaultNumberScore;
import static com.uptang.cloud.score.handler.PrimitiveResolver.Double_;
import static com.uptang.cloud.score.handler.PrimitiveResolver.getConverter;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:20
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class HealthAnalysisEventListener extends AbstractAnalysisEventListener<HealthScoreDTO> {

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final ISubjectService scoreService =
            ApplicationContextHolder.getBean(ISubjectService.class);

    private final IScoreStatusService scoreStatusService =
            ApplicationContextHolder.getBean(IScoreStatusService.class);

    public HealthAnalysisEventListener(RequestParameter excel) {
        super(excel);
    }

    @Override
    public List<Subject> getSubjects(Map<Integer, Object> rawData,
                                     Integer lineNumber,
                                     int startIndex,
                                     List<GradeCourseDTO> gradeCourse) {

        List<GradeCourseDTO> courses = gradeCourse.stream()
                .filter(course -> course.getScoreType() == HEALTH)
                .collect(Collectors.toList());

        List<Subject> subjects = new ArrayList<>();
        for (GradeCourseDTO course : courses) {
            Set<Map.Entry<Integer, String>> entries = headMap.entrySet();
            for (Map.Entry<Integer, String> entry : entries) {
                String subjectName = entry.getValue();
                if (course.getSubjectName().equals(subjectName)) {
                    subjects.add(getSubject(rawData.get(entry.getKey()), course));
                }
            }
        }

        return subjects;
    }

    @Override
    public void doInvoke(List<HealthScoreDTO> data, RequestParameter excel, AnalysisContext context) {
        // 批量插入履历表
        List<AcademicResume> resumes = data.stream().map(HealthScoreDTO::getResume).collect(Collectors.toList());
        Map<Integer, List<AcademicResume>> groupList = getGroupList(resumes);
        List<Map<Long, Long>> maps = resumeService.batchSave(groupList);

        // 设置履历ID以及批量插入科目
        for (Map<Long, Long> map : maps) {
            Set<Map.Entry<Long, Long>> entries = map.entrySet();
            for (Map.Entry<Long, Long> entry : entries) {
                data.forEach(excelDto -> excelDto.getSubjects().forEach(subject -> {
                    if (entry.getKey().compareTo(subject.getStudentId()) == 0) {
                        subject.setResumeId(entry.getValue());
                    }
                }));
            }
        }

        List<List<Long>> lists;
        try {
            // 分批
            List<Subject> subjects = data.stream()
                    .map(HealthScoreDTO::getSubjects)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            // 插入科目
            lists = scoreService.batchInsert(getGroupList(subjects));
        } catch (Exception e) {
            undo(maps);
            throw new BusinessException(e);
        }

        try {
            // 插入免测学生
            scoreService.exemption(excel);
            // 状态表
            List<ScoreStatus> statuses = Utils.getScoreStatuses(maps);
            scoreStatusService.batchInsert(statuses);
        } catch (Exception e) {
            undo(maps);
            scoreService.batchDelete(lists.stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList()), HEALTH);
            throw new BusinessException(e);
        }
    }


    /**
     * 回滚履历表
     *
     * @param maps
     */
    private void undo(List<Map<Long, Long>> maps) {
        if (maps != null && maps.size() > 0) {
            maps.stream()
                    .map(Map::values)
                    .collect(Collectors.toList())
                    .forEach(resumeService::removeByIds);
        }
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(HEALTH);
    }

    /**
     * @param value
     * @param course
     * @return
     */
    private Subject getSubject(Object value, GradeCourseDTO course) {
        Subject subject = new Subject();
        subject.setScoreType(HEALTH);
        subject.setCode(course.getId());
        subject.setName(course.getSubjectName());
        subject.setScoreText(Strings.EMPTY);
        subject.setScoreNumber(Calculator.UNSIGNED_SMALLINT_MAX_VALUE);

        if (value == null) {
            return subject;
        }

        PrimitiveResolver converter = getConverter(value.getClass());
        switch (converter) {
            // 字符串类型
            case String_:
                subject.setScoreText((String) converter.convert(value));
                break;
            // 数值类型
            default:
                Double convert = (Double) Double_.convert(value);
                subject.setScoreNumber(defaultNumberScore(convert));
        }
        return subject;
    }
}

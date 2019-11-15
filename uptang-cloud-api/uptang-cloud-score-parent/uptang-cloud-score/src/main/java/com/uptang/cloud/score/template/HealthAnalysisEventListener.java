package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.ISubjectService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.HEALTH;
import static com.uptang.cloud.score.common.util.Calculator.defaultNumberScore;
import static com.uptang.cloud.score.handler.PrimitiveResolver.Double_;
import static com.uptang.cloud.score.handler.PrimitiveResolver.String_;

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

    public HealthAnalysisEventListener(RequestParameter excel) {
        super(excel);
    }

    @Override
    public List<Subject> getSubjects(Map<Integer, Object> rawData,
                                     Integer lineNumber, int startIndex,
                                     List<GradeCourseDTO> gradeCourse) {

        List<GradeCourseDTO> courses = gradeCourse.stream()
                .filter(course -> course.getScoreType() == HEALTH)
                .collect(Collectors.toList());

        // FIXME 没成绩
        List<Subject> subjects = new ArrayList<>();
        Iterator<GradeCourseDTO> iterator = courses.iterator();
        for (int i = startIndex; i < rawData.size(); i++) {
            while (iterator.hasNext()) {
                GradeCourseDTO course = iterator.next();
                if (course.getOrderNumber().compareTo(i) == 0) {
                    Object value = rawData.get(course.getOrderNumber());
                    subjects.add(getSubject(value, course));
                    iterator.remove();
                    break;
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

        switch (course.getDataType()) {
            case NUMBER:
                // 数值类型
                Double convert = (Double) Double_.convert(value);
                subject.setScoreNumber(defaultNumberScore(convert));
                break;
            case String:
            default:
                // 字符串类型
                subject.setScoreText((String) String_.convert(value));
        }
        return subject;
    }
}

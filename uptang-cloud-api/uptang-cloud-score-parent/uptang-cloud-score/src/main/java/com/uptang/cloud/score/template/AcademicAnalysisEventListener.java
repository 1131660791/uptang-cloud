package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.dto.ExcelDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.service.ISubjectService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:20
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 * 抽出去类多 不抽又看它不顺眼...... FIXME 后续优化再说
 */
@Slf4j
public class AcademicAnalysisEventListener extends AbstractAnalysisEventListener<ExcelDTO> {

    private final IAcademicResumeService resumeService =
            ApplicationContextHolder.getBean(IAcademicResumeService.class);

    private final ISubjectService scoreService =
            ApplicationContextHolder.getBean(ISubjectService.class);

    private final IScoreStatusService scoreStatusService =
            ApplicationContextHolder.getBean(IScoreStatusService.class);

    public AcademicAnalysisEventListener(RequestParameter excel) {
        super(excel);
    }

    @Override
    public List<Subject> getSubjects(Map<Integer, Object> rawData,
                                     Integer lineNumber,
                                     int startIndex,
                                     List<GradeCourseDTO> gradeCourse) {
        return Utils.getSubjects(rawData, lineNumber, startIndex, gradeCourse, ScoreTypeEnum.ACADEMIC);
    }

    /**
     * @param data
     * @param excel
     * @param context
     */
    @Override
    public void doInvoke(List<ExcelDTO> data, RequestParameter excel, AnalysisContext context) {
        // 如果重复导入则覆盖原有的分数
        if (resumeService.importAgain(excel)) {
            override(data, excel);
            return;
        }

        // 批量插入履历表
        List<AcademicResume> resumes = data.stream().map(ExcelDTO::getResume).collect(Collectors.toList());
        Map<Integer, List<AcademicResume>> groupList = getGroupList(resumes);
        List<Map<Long, Long>> maps = resumeService.batchSave(groupList);

        try {
            setResumeId(data, maps);

            // 插入科目
            Map<Integer, List<Subject>> subjects = getGroupList(Utils.convert2List(data));
            if (subjects == null || subjects.size() == 0) {
                log.error("导入Excel。插入Subject失败，原因没有Subject需要被插入\n " +
                        "客户端参数 ==> {} \n 解析Excel数据 ==> {}", excel, data);
                throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR.getDesc());
            }
            scoreService.batchInsert(subjects);
            // 状态表
            List<ScoreStatus> statuses = Utils.getScoreStatuses(maps);
            scoreStatusService.batchInsert(statuses);
        } catch (Exception e) {
            maps.stream()
                    .map(Map::values)
                    .collect(Collectors.toList())
                    .forEach(resumeService::removeByIds);
            throw new BusinessException(e);
        }
    }

    /**
     * 设置履历ID
     *
     * @param data
     * @param maps
     */
    private void setResumeId(List<ExcelDTO> data, List<Map<Long, Long>> maps) {
        // 设置履历ID以及批量插入科目
        for (Map<Long, Long> map : maps) {
            Iterator<Map.Entry<Long, Long>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                // 设置履历ID
                Map.Entry<Long, Long> next = iterator.next();
                data.forEach(excelDto -> excelDto.getSubjects().forEach(subject -> {
                    if (next.getKey().compareTo(subject.getStudentId()) == 0) {
                        subject.setResumeId(next.getValue());
                    }
                }));
            }
        }
    }

    /**
     * 如果重复导入则覆盖原有的分数
     *
     * @param data      Excel数据
     * @param parameter web参数
     */
    private void override(List<ExcelDTO> data, RequestParameter parameter) {
        try {
            AcademicResume resume = new AcademicResume();
            resume.setScoreType(parameter.getScoreType());
            resume.setSchoolId(parameter.getSchoolId());
            resume.setGradeId(parameter.getGradeId());
            resume.setClassId(parameter.getClassId());
            resume.setSemesterId(parameter.getSemesterId());
            List<Long> ids = setResumeId(data, resume);
            scoreService.batchDelete(ids, ScoreTypeEnum.ACADEMIC);

            // 插入科目
            Map<Integer, List<Subject>> subjects = getGroupList(Utils.convert2List(data));
            if (subjects == null || subjects.size() == 0) {
                log.error("导入Excel并覆盖原有数据失败，原因没有Subject需要被插入\n " +
                        "客户端参数 ==> {} \n 解析Excel数据 ==> {}", parameter, data);
                throw new BusinessException(ResponseCodeEnum.SYSTEM_ERROR.getDesc());
            }

            scoreService.batchInsert(subjects);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 设置Resume履历ID
     *
     * @param data
     * @param resume
     */
    private List<Long> setResumeId(List<ExcelDTO> data, AcademicResume resume) {
        List<AcademicResume> resumeIds = resumeService.resume(resume);
        if (resumeIds != null && resumeIds.size() > 0) {
            for (ExcelDTO excelDto : data) {
                for (AcademicResume resumeId : resumeIds) {
                    // 学生名称及学籍号相同则说明是同一人
                    if (resumeId.getStudentCode().equals(excelDto.getStudentCode())
                    && resumeId.getStudentName().equals(excelDto.getStudentName())) {
                        excelDto.getResume().setId(resumeId.getId());
                        excelDto.getSubjects().forEach(subject -> subject.setResumeId(resumeId.getId()));
                    }
                }
            }
            return resumeIds.stream().map(AcademicResume::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.ACADEMIC);
    }
}

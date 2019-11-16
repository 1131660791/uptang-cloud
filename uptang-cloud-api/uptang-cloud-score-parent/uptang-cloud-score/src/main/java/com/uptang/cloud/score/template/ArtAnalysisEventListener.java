package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.dto.ExcelDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategyFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 20:52
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public class ArtAnalysisEventListener extends AbstractAnalysisEventListener<ExcelDTO> {

    public ArtAnalysisEventListener(RequestParameter excel) {
        super(excel);
    }

    @Override
    public List<Subject> getSubjects(Map<Integer, Object> rawData,
                                     Integer lineNumber,
                                     int startIndex,
                                     List<GradeCourseDTO> gradeCourse) {
        return Utils.getSubjects(rawData, lineNumber, startIndex, gradeCourse, ScoreTypeEnum.ART);
    }

    @Override
    public void doInvoke(List<ExcelDTO> data, RequestParameter excel, AnalysisContext context) {
        new AcademicAnalysisEventListener(excel).doInvoke(data, excel, context);
    }


    @Override
    public ExcelProcessorStrategy getStrategy() {
        return ExcelProcessorStrategyFactory.getStrategy(ScoreTypeEnum.ART);
    }
}

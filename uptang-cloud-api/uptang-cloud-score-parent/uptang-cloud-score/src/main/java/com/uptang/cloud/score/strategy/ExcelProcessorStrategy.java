package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 17:52
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public interface ExcelProcessorStrategy<T> {

    /**
     * Excel 表头
     *
     * @param headMap         表头数据
     * @param gradeCourse 年级科目信息
     */
    void headMap(Map<Integer, String> headMap, List<GradeCourseDTO> gradeCourse);

    /**
     * Excel行数据检查
     *
     * @param sheetData Excel数据
     * @param context   Excel 解析上下文
     * @param excel
     * @return 数据库Model或者Entity....
     */
    void check(List<T> sheetData, AnalysisContext context, RequestParameter excel);
}

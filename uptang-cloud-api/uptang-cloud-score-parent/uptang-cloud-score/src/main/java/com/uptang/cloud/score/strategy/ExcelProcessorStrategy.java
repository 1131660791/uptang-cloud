package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.enums.SemesterEnum;

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
     * @param headMap 表头数据
     */
    void headMap(Map<Integer, String> headMap);

    /**
     * Excel行数据检查
     *
     * @param rawData      Excel行数据
     * @param context      Excel 解析上下文
     * @param userId       用户ID
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     * @return 数据库Model或者Entity....
     */
    boolean check(T rawData, AnalysisContext context,
                Long userId, Long gradeId, Long classId, Long schoolId, SemesterEnum semesterCode);
}

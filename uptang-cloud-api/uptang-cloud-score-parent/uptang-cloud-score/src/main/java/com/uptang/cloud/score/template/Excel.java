package com.uptang.cloud.score.template;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 23:56
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public interface Excel {

    /**
     * 解析Excel
     *
     * @param excelType    Excel类型
     * @param type         Excel数据类型
     * @param userId       用户ID
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     * @param excelDTO
     */
    void analysis(ExcelTypeEnum excelType, ImportFromExcelDTO excelDTO);
}

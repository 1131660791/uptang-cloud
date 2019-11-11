package com.uptang.cloud.score.service;

import com.uptang.cloud.score.dto.ImportFromExcelDTO;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:37
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public interface IExcelDataServiceProcessor {

    /**
     * 处理Excel数据
     *
     * @param token        当前用户登录凭证
     * @param userId       当前用户ID
     * @param type         成绩类型
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     * @param excelDTO
     * @return
     */
    void processor(ImportFromExcelDTO excelDTO);
}

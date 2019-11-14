package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.StuListDTO;
import com.uptang.cloud.score.dto.StudentRequestDTO;
import com.uptang.cloud.score.service.IRestCallerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 18:02
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Component
public class HealthScoreExcelProcessorStrategy
        implements ExcelProcessorStrategy<HealthScoreDTO>, InitializingBean {

    @Autowired
    private IRestCallerService restCallerService;

    @Override
    public void headMap(Map<Integer, String> headMap, List<GradeCourseDTO> gradeCourse) {
        Utils.headCheck(headMap, gradeCourse, 9, ScoreTypeEnum.HEALTH);
    }

    @Override
    public void check(List<HealthScoreDTO> sheetData, AnalysisContext context, RequestParameter excel) {
        Utils.setHealthUserInfo(sheetData, excel,
                () -> getStuInfo(excel, null, 1L, 200L),
                (pageNum, pageSize) -> getStuInfo(excel, null, pageNum, pageSize),
                (excelDto) -> getStuInfo(excel, excelDto, 0L, 0L));

        for (HealthScoreDTO sheetDatum : sheetData) {
            Utils.checkUserInfo(sheetDatum.getResume());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelProcessorStrategyFactory.register(ScoreTypeEnum.HEALTH, this);
    }

    /**
     * 获取学生信息
     *
     * @param excel    前端请求参数
     * @param pageNum  页码
     * @param pageSize 条数
     */
    private StuListDTO getStuInfo(RequestParameter excel, HealthScoreDTO excelDto, Long pageNum, Long pageSize) {
        StudentRequestDTO student = new StudentRequestDTO();
        student.setToken(excel.getToken());
        student.setGradeId(excel.getGradeId());
        student.setSchoolId(excel.getSchoolId());
        if (excelDto != null) {
            student.setStudentName(excelDto.getStudentName());
            student.setStudentCode(excelDto.getStudentCode());
            student.setClassId(excel.getClassId());
        } else {
            student.setOffset(pageNum);
            student.setRows(pageSize);
        }
        return restCallerService.studentList(student);
    }
}

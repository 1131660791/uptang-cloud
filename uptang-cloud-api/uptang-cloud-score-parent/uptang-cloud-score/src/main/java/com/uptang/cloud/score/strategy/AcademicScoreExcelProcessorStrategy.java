package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.dto.ExcelDto;
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
 * @createtime : 2019-11-08 18:01
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Component
public class AcademicScoreExcelProcessorStrategy implements ExcelProcessorStrategy<ExcelDto>, InitializingBean {

    @Autowired
    private IRestCallerService restCallerService;

    @Override
    public void headMap(Map<Integer, String> headMap, List<GradeCourseDTO> gradeCourse) {
        Utils.headCheck(headMap, gradeCourse, 4, ScoreTypeEnum.ACADEMIC);
    }

    /**
     * 将每一页学生分页数据与整个Sheet页内容进行匹配
     *
     * @param sheetData Excel数据
     * @param context   Excel 解析上下文
     * @param excel
     */
    @Override
    public void check(List<ExcelDto> sheetData, AnalysisContext context, RequestParameter excel) {
        Utils.setUserInfo(sheetData,
                excel,
                () -> getStuInfo(excel, null, 1, 200),
                (pageNum, pageSize) -> getStuInfo(excel, null, pageNum, pageSize),
                (excelDto) -> getStuInfo(excel, excelDto, 0L, 0L));

        for (ExcelDto sheetDatum : sheetData) {
            Utils.checkUserInfo(sheetDatum.getResume());
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelProcessorStrategyFactory.register(ScoreTypeEnum.ACADEMIC, this);
    }

    /**
     * 获取学生信息
     *
     * @param excel    前端请求参数
     * @param pageNum  页码
     * @param pageSize 条数
     */
    private StuListDTO getStuInfo(RequestParameter excel, ExcelDto excelDto, long pageNum, long pageSize) {
        StudentRequestDTO student = Utils.getStudentRequestDTO(excel, excelDto, pageNum, pageSize);
        return restCallerService.studentList(student);
    }
}

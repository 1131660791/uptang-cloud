package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.annotation.ArchiveCheck;
import com.uptang.cloud.score.annotation.JobSwitchCheck;
import com.uptang.cloud.score.common.converter.ImportFromExcelConverter;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.vo.ImportFromExcelVo;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:32
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@RestController
@RequestMapping("/v1/excel")
@Api(value = "AcademicResumeController", tags = {"Excel导入成绩"})
public class ExcelController extends BaseController {

    private final IExcelDataServiceProcessor excelDataServiceProcessor;

    public ExcelController(IExcelDataServiceProcessor excelDataServiceProcessor) {
        this.excelDataServiceProcessor = excelDataServiceProcessor;
    }

    @PostMapping("/{type}/{schoolId}/{gradeId}/{classId}/{semesterId}/{semesterName}")
    @ArchiveCheck
    @JobSwitchCheck
    @ApiOperation(value = "Excel导入成绩", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterName", value = "学期名称", paramType = "path", required = true),
    })
    public ApiOut<String> batchInsert(@PathVariable("type") @NotNull Integer type,
                                      @PathVariable("gradeId") @NotNull Long gradeId,
                                      @PathVariable("classId") @NotNull Long classId,
                                      @PathVariable("schoolId") @NotNull Long schoolId,
                                      @PathVariable("semesterId") @NotNull Long semesterId,
                                      @PathVariable("semesterName") @NotNull String semesterName) {
        ImportFromExcelVo importFromExcel = new ImportFromExcelVo();
        importFromExcel.setSchoolId(schoolId);
        importFromExcel.setScoreType(ScoreTypeEnum.code(type));
        importFromExcel.setClassId(classId);
        importFromExcel.setGradeId(gradeId);
        importFromExcel.setSemesterId(semesterId);

        RequestParameter excelDTO = ImportFromExcelConverter.INSTANCE.toModel(importFromExcel);
        excelDTO.setToken(getToken());
        excelDTO.setUserId(getUserId());
        excelDTO.setSemesterName(semesterName);

        excelDataServiceProcessor.processor(excelDTO);
        return ApiOut.newPrompt("成功录入成绩");
    }
}

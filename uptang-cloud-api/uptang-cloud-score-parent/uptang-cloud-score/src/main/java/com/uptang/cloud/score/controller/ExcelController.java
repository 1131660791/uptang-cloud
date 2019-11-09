package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/{type}/{gradeId}/{classId}/{schoolId}/{semesterCode}")
    @ApiOperation(value = "Excel导入成绩", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩",
                    paramType = "path", required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterCode", value = "学期编码", paramType = "path", required = true),
    })
    public ApiOut<String> batchInsert(@PathVariable("type") @NotNull Integer type,
                                      @PathVariable("gradeId") @NotNull Long gradeId,
                                      @PathVariable("classId") @NotNull Long classId,
                                      @PathVariable("schoolId") @NotNull Long schoolId,
                                      @PathVariable("semesterCode") @NotNull Integer semesterCode) {

        SemesterEnum semesterEnum = SemesterEnum.code(semesterCode);
        if (semesterEnum.compareTo(SemesterEnum.UNKNOWN) == 1) {
            return ApiOut.newParameterRequiredResponse("非法的学期编码");
        }

        excelDataServiceProcessor.processor(getToken(),
                getUserId(), type, gradeId, classId, schoolId, semesterEnum);
        return ApiOut.newPrompt("成功录入成绩");
    }
}

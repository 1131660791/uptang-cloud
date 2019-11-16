package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.dto.RequestParameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author Lee.m.yin <lmy@uptong.com.cn>
 * @version 4.0.0
 * @date 2019-11-11
 */
@RestController
@RequestMapping("/v1/excel")
@Api(tags = "Excel导入成绩")
public class ExcelController extends BaseController {

    private final IExcelDataServiceProcessor excelDataServiceProcessor;

    public ExcelController(IExcelDataServiceProcessor excelDataServiceProcessor) {
        this.excelDataServiceProcessor = excelDataServiceProcessor;
    }


    @ApiOperation(value = "Excel导入成绩", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0:学业成绩 1:体质健康 2:艺术成绩", paramType = "path", dataTypeClass = int.class, required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", dataTypeClass = long.class, required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", dataTypeClass = long.class, required = true),
            @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", dataTypeClass = long.class, required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "query", dataTypeClass = long.class),
            @ApiImplicitParam(name = "semesterName", value = "学期名称", paramType = "query", required = true),
    })
    @PostMapping("/{type}/{schoolId}/{gradeId}/{semesterId}")
    public ApiOut<String> batchInsert(@PathVariable("type") @NotNull Integer type,
                                      @PathVariable("gradeId") @NotNull Long gradeId,
                                      @PathVariable("schoolId") @NotNull Long schoolId,
                                      @PathVariable("semesterId") @NotNull Long semesterId,
                                      @RequestParam(value = "classId", required = false) Long classId,
                                      @RequestParam("semesterName") @NotNull String semesterName) {

        RequestParameter parameter = RequestParameter.builder()
                .scoreType(ScoreTypeEnum.code(type))
                .schoolId(schoolId).gradeId(gradeId).classId(classId)
                .semesterId(semesterId).semesterName(semesterName)
                .token(getToken()).userId(getUserId())
                .build();

        excelDataServiceProcessor.processor(parameter);
        return ApiOut.newPrompt("成功录入成绩");
    }
}

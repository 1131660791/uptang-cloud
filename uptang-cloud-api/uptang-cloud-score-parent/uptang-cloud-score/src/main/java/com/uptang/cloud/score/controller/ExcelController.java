package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.annotation.ArchiveCheck;
import com.uptang.cloud.score.annotation.JobSwitchCheck;
import com.uptang.cloud.score.common.converter.ImportFromExcelConverter;
import com.uptang.cloud.score.common.vo.ImportFromExcelVo;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ArchiveCheck
    @JobSwitchCheck
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "Excel导入成绩", response = String.class)
    public ApiOut<String> batchInsert(@RequestParam ImportFromExcelVo importFromExcel) {
        ImportFromExcelDTO excelDTO = ImportFromExcelConverter.INSTANCE.toModel(importFromExcel);
        excelDTO.setToken(getToken());
        excelDTO.setUserId(getUserId());
        excelDataServiceProcessor.processor(excelDTO);
        return ApiOut.newPrompt("成功录入成绩");
    }
}

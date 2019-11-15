package com.uptang.cloud.score.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ModuleSwitchDto;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.RestRequestDto;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.template.ExcelTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.ART;
import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.HEALTH;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:39
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Service
public class ExcelDataServiceProcessor extends ExcelTemplate implements IExcelDataServiceProcessor {

    @Autowired
    private IScoreStatusService statusService;

    @Autowired
    private IRestCallerService restCallerService;

    @Autowired
    private IAcademicResumeService resumeService;

    @Override
    public void processor(RequestParameter parameter) {
        preCheck(parameter);
        if (parameter.getScoreType() != null) {
            super.analysis(ExcelTypeEnum.XLS, parameter);
        }
    }

    /**
     * 录入检查
     *
     * @param parameter
     */
    private void preCheck(RequestParameter parameter) {
        // 艺术类和体质健康类成绩一年只能导入一次
        @NotNull ScoreTypeEnum scoreType = parameter.getScoreType();
        if (scoreType == HEALTH || scoreType == ART) {
            AcademicResume resume = new AcademicResume();
            resume.setScoreType(scoreType);
            resume.setSchoolId(parameter.getSchoolId());
            resume.setGradeId(parameter.getGradeId());
            resume.setClassId(parameter.getClassId());
            resume.setSemesterId(parameter.getSemesterId());
            if (resumeService.importAgain(resume)) {
                throw new BusinessException(scoreType.getDesc() + "已录入");
            }
        }

        // 数据是否归档
        boolean archive = statusService.isArchive(parameter.getSchoolId(),
                parameter.getGradeId(),
                parameter.getSemesterId(),
                parameter.getScoreType());
        if (archive) {
            throw new BusinessException("数据已归档");
        }

        // 权限校验
        RestRequestDto restRequestDto = new RestRequestDto();
        restRequestDto.setToken(parameter.getToken());
        boolean hasPromission = restCallerService.promissionCheck(restRequestDto);
        if (!hasPromission) {
            throw new BusinessException("无权操作");
        }

        // 任务是否开放
        ModuleSwitchDto moduleSwitch = ModuleSwitchDto.builder().gradeId(parameter.getGradeId()).build();
        moduleSwitch.setToken(parameter.getToken());
        boolean isOpen = restCallerService.moduleSwitch(moduleSwitch);
        if (!isOpen) {
            throw new BusinessException("任务未开放");
        }
    }
}

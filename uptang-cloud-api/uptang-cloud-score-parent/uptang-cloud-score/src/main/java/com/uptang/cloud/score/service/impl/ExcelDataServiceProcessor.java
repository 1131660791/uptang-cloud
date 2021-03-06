package com.uptang.cloud.score.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.dto.ModuleSwitchDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.RestRequestDTO;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.template.ExcelTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.ART;
import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.HEALTH;

/**
 * @author Lee.m.yin <lmy@uptong.com.cn>
 * @version 4.0.0
 * @date 2019-11-11
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
        if (Objects.isNull(parameter.getScoreType())) {
            return;
        }

        preCheck(parameter);
        super.analysis(ExcelTypeEnum.XLS, parameter);
    }

    /**
     * 录入检查
     *
     * @param parameter
     */
    private void preCheck(RequestParameter parameter) {
        // 艺术类和体质健康类成绩一年只能导入一次
        ScoreTypeEnum scoreType = parameter.getScoreType();
        if (scoreType == HEALTH || scoreType == ART) {
            if (resumeService.importAgain(parameter)) {
                throw new BusinessException(scoreType.getDesc() + "已录入");
            }
        }

        // 数据是否归档
        boolean archive = statusService.isArchive(parameter.getSchoolId(), parameter.getGradeId(),
                parameter.getSemesterId(), parameter.getScoreType());
        if (archive) {
            throw new BusinessException("数据已归档");
        }

        // 权限校验
        RestRequestDTO restRequestDto = new RestRequestDTO();
        restRequestDto.setToken(parameter.getToken());
        boolean hasPermission = restCallerService.permissionCheck();
        if (!hasPermission) {
            throw new BusinessException("无权操作");
        }

        // 任务是否开放
        ModuleSwitchDTO moduleSwitch = ModuleSwitchDTO.builder().gradeId(parameter.getGradeId()).build();
        moduleSwitch.setToken(parameter.getToken());
        boolean isOpen = restCallerService.moduleSwitch(moduleSwitch);
        if (!isOpen) {
            throw new BusinessException("任务未开放");
        }
    }
}

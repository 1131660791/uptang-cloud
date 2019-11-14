package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.feign.AcademicResumeProvider;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.starter.web.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 15:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@RestController
@RequestMapping("/v1/resume")
@Api(value = "AcademicResumeController", tags = {"学业履历管理"})
public class AcademicResumeController extends BaseController implements AcademicResumeProvider {

    private final IAcademicResumeService academicResumeService;

    public AcademicResumeController(IAcademicResumeService academicResumeService) {
        this.academicResumeService = academicResumeService;
    }
}

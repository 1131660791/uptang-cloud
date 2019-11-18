package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:24
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@RestController
@RequestMapping("/v1/state")
@Api(value = "ScoreStatusController", tags = {"履历状态管理"})
public class ScoreStatusController extends BaseController {

    private final IScoreStatusService scoreStatusService;

    public ScoreStatusController(IScoreStatusService scoreStatusService) {
        this.scoreStatusService = scoreStatusService;
    }

    @PutMapping("/archive/{type}/{school-id}/{grade-id}/{semester-id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "school-id", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "grade-id", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semester-id", value = "学期ID", paramType = "path", required = true),
    })
    @ApiOperation(value = "归档", response = Boolean.class)
    public ApiOut<Boolean> archive(@PathVariable("school-id") @NotNull Long schoolId,
                                   @PathVariable("grade-id") @NotNull Long gradeId,
                                   @PathVariable("semester-id") @NotNull Long semesterId,
                                   @PathVariable("type") @NotNull Integer type) {
        boolean archive = scoreStatusService.archive(schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
        String message = archive ? "成功" : "失败";
        return ApiOut.newPrompt(message);
    }

    @PutMapping("/undoArchive/{type}/{school-id}/{grade-id}/{semester-id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "school-id", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "grade-id", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semester-id", value = "学期ID", paramType = "path", required = true),
    })
    @ApiOperation(value = "撤销归档", response = String.class)
    public ApiOut<Boolean> undoArchive(@PathVariable("school-id") @NotNull Long schoolId,
                                       @PathVariable("grade-id") @NotNull Long gradeId,
                                       @PathVariable("semester-id") @NotNull Long semesterId,
                                       @PathVariable("type") @NotNull Integer type) {

        boolean undoArchive = scoreStatusService.undoArchive(schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
        String message = undoArchive ? "成功" : "失败";
        return ApiOut.newPrompt(message);
    }

    @PutMapping("/show/{type}/{school-id}/{grade-id}/{semester-id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "school-id", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "grade-id", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semester-id", value = "学期ID", paramType = "path", required = true),
    })
    @ApiOperation(value = "公示", response = Boolean.class)
    public ApiOut<String> show(@PathVariable("school-id") @NotNull Long schoolId,
                               @PathVariable("grade-id") @NotNull Long gradeId,
                               @PathVariable("semester-id") @NotNull Long semesterId,
                               @PathVariable("type") @NotNull Integer type) {
        boolean show =
                scoreStatusService.show(getToken(), schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
        String message = show ? "成功" : "失败";
        return ApiOut.newPrompt(message);
    }

    @PutMapping("/cancel/{type}/{school-id}/{grade-id}/{semester-id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "school-id", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "grade-id", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semester-id", value = "学期ID", paramType = "path", required = true),
    })
    @ApiOperation(value = "撤销公示", response = Boolean.class)
    public ApiOut<Boolean> cancel(@PathVariable("school-id") @NotNull Long schoolId,
                                  @PathVariable("grade-id") @NotNull Long gradeId,
                                  @PathVariable("semester-id") @NotNull Long semesterId,
                                  @PathVariable("type") @NotNull Integer type) {
        boolean cancel = scoreStatusService.cancel(schoolId, gradeId, semesterId, ScoreTypeEnum.code(type));
        String message = cancel ? "成功" : "失败";
        return ApiOut.newPrompt(message);
    }
}

package com.uptang.cloud.score.controller;

import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.score.common.converter.ScoreConverter;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.vo.ScoreVO;
import com.uptang.cloud.score.feign.ScoreProvider;
import com.uptang.cloud.score.service.IScoreService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Slf4j
@RestController
@RequestMapping("/v1/score")
@Api(value = "ScoreController", tags = {"成绩管理"})
public class ScoreController extends BaseController implements ScoreProvider {

    private final IScoreService scoreService;

    public ScoreController(IScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "成绩详情", response = ScoreVO.class)
    @ApiImplicitParam(name = "id", value = "成绩ID", paramType = "path", required = true)
    public ApiOut<ScoreVO> getDetail(@PathVariable("id") @NotNull Long id) {
        if (NumberUtils.isNotPositive(id)) {
            return ApiOut.newParameterRequiredResponse("成绩ID");
        }

        Score score = scoreService.getById(id);
        return ApiOut.newSuccessResponse(ScoreConverter.INSTANCE.toVo(score));
    }

    @PostMapping
    @ApiOperation(value = "成绩录入", response = ScoreVO.class)
    @ApiParam(name = "成绩", value = "传入json格式", required = true)
    public ApiOut<ScoreVO> save(@RequestBody ScoreVO scoreVO) {
        boolean saved = scoreService.save(ScoreConverter.INSTANCE.toModel(scoreVO));
        String message = !saved ? "成绩录入失败" : "成功录入成绩";
        return ApiOut.newParameterRequiredResponse(message);
    }

    @PutMapping(path = "/{id}/{subject}")
    @ApiOperation(value = "成绩修改", response = ScoreVO.class)
    @ApiParam(name = "成绩", value = "传入json格式", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成绩ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "subject", value = "科目编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "scoreText", value = "等级(及格,不及格.)", paramType = "query"),
            @ApiImplicitParam(name = "scoreNumber", value = "数字分数", paramType = "query"),
    })
    public ApiOut<ScoreVO> update(@PathVariable("id") @NotNull Long id,
                                  @PathVariable("subject") @NotNull Integer subject,
                                  @RequestParam(value = "scoreText", required = false) String scoreText,
                                  @RequestParam(value = "scoreNumber", required = false) Integer scoreNumber) {
        final boolean noneScore = StringUtils.isBlank(scoreText) && scoreNumber == 0 || scoreNumber == null;
        Assert.isTrue(noneScore, "成绩不能为空");

        ScoreVO scoreVO = new ScoreVO();
        scoreVO.setId(id);
        scoreVO.setSubject(subject);
        scoreVO.setScoreNumber(scoreNumber);
        scoreVO.setScoreText(scoreText);

        Score score = ScoreConverter.INSTANCE.toModel(scoreVO);
        boolean updated = scoreService.update().update(score);
        String message = !updated ? "修改成绩失败" : "成绩修改成功";
        return ApiOut.newParameterRequiredResponse(message);
    }
}

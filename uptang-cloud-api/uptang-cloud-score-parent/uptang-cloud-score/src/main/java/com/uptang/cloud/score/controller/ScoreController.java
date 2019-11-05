package com.uptang.cloud.score.controller;

import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.score.common.converter.ScoreConverter;
import com.uptang.cloud.score.common.enums.LevelEnum;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.vo.ScoreVO;
import com.uptang.cloud.score.feign.ScoreProvider;
import com.uptang.cloud.score.service.ScoreService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @ApiOperation(value = "成绩详情", response = ScoreVO.class)
    @ApiImplicitParam(name = "id", value = "成绩ID", paramType = "path", required = true)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<ScoreVO> getDetail(@PathVariable("id") @NotNull Long id) {
        if (NumberUtils.isNotPositive(id)) {
            return ApiOut.newParameterRequiredResponse("成绩ID");
        }

        Score score = new Score();
        score.setId(id);
        score.setLevel(LevelEnum.BEST);
        score.setCreatedTime(new Date());
        return ApiOut.newSuccessResponse(ScoreConverter.INSTANCE.toVo(score));
    }
}

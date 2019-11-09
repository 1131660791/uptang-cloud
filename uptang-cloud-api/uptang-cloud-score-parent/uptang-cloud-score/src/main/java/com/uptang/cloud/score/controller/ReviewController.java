package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.converter.ReviewConverter;
import com.uptang.cloud.score.common.model.Review;
import com.uptang.cloud.score.common.vo.ReviewVO;
import com.uptang.cloud.score.feign.ReviewProvider;
import com.uptang.cloud.score.service.IReviewService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:49
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 这里叫review不大合适 叫状态应该更贴切
 */
@RestController
@RequestMapping("/v1/review")
@Api(value = "ReviewController", tags = {"审议状态管理"})
public class ReviewController extends BaseController implements ReviewProvider {

    private final IReviewService reviewService;

    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "/{id}/{objection}/{type}")
    @ApiOperation(value = "异议提交", response = ReviewVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审议进度ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "objection", value = "异议 0 无状态 1 异议处理期", paramType = "path", required = true),
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
    })
    public ApiOut<ReviewVO> submit(@PathVariable("id") @NotNull Long id,
                                   @PathVariable("objection") @NotNull Integer objection,
                                   @PathVariable("type") @NotNull Integer type,
                                   @RequestParam(value = "objectionDesc") String objectionDesc) {

        ReviewVO reviewVO = new ReviewVO();
        reviewVO.setId(id);
        reviewVO.setType(type);
        reviewVO.setObjection(objection);
        reviewVO.setObjectionDesc(objectionDesc);

        Review review = ReviewConverter.INSTANCE.toModel(reviewVO);
        boolean saved = reviewService.submit(review);
        String message = !saved ? "异议提交失败" : "异议已提交";
        return ApiOut.newPrompt(message);
    }

    @PutMapping(path = "/{id}/{objection}")
    @ApiOperation(value = "异议审核", response = ReviewVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "审议进度ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "objection", value = "异议 0 无状态 1 异议处理期", paramType = "path", required = true)
    })
    public ApiOut<ReviewVO> verify(@PathVariable("id") @NotNull Long id,
                                   @PathVariable("objection") @NotNull Integer objection) {
        ReviewVO reviewVO = new ReviewVO();
        reviewVO.setId(id);
        reviewVO.setObjection(objection);
        Review review = ReviewConverter.INSTANCE.toModel(reviewVO);
        boolean saved = reviewService.verify(review);
        String message = !saved ? "异议修改失败" : "异议已成功修改";
        return ApiOut.newPrompt(message);
    }
}

package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ObjectionRecord;
import com.uptang.cloud.score.common.vo.ObjectionRecordVO;
import com.uptang.cloud.score.service.IObjectionRecordService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.uptang.cloud.score.common.converter.ObjectionRecordConverter.INSTANCE;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:35
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@RestController
@RequestMapping("/v1/record")
@Api(value = "ObjectionRecordController", tags = {"异议管理"})
public class ObjectionRecordController extends BaseController {

    private final IObjectionRecordService objectionRecordService;

    public ObjectionRecordController(IObjectionRecordService objectionRecordService) {
        this.objectionRecordService = objectionRecordService;
    }

    @GetMapping("/{type}/{resumeId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "resumeId", value = "履历ID", paramType = "path", required = true)
    })
    @ApiOperation(value = "异议列表", response = ObjectionRecordVO.class)
    public ApiOut<List<ObjectionRecordVO>> list(@PathVariable("resumeId") @NotNull Long resumeId,
                                                @PathVariable("type") @NotNull Integer type,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<ObjectionRecord> page = objectionRecordService.page(ScoreTypeEnum.code(type), resumeId, pageNum, pageSize);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(page, models -> {
            if (page == null || page.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @PostMapping
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "提交异议", response = Boolean.class)
    public ApiOut<Boolean> add(@RequestBody ObjectionRecordVO objectionRecord) {
        if (objectionRecord.getScoreType() == null) {
            return ApiOut.newPrompt("成绩类型不能为空");
        }

        if (objectionRecord.getResumeId() == null) {
            return ApiOut.newPrompt("履历ID不能为空");
        }

        if (StringUtils.isBlank(objectionRecord.getDescription())) {
            return ApiOut.newPrompt("异议描述不能为空");
        }

        objectionRecord.setCreatorId(getUserId());
        objectionRecordService.add(INSTANCE.toModel(objectionRecord));
        return ApiOut.newSuccessResponse(true);
    }

    @PutMapping
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "异议审核", response = Boolean.class)
    public ApiOut<Boolean> update(@RequestBody ObjectionRecordVO objectionRecord) {
        if (objectionRecord.getScoreType() == null) {
            return ApiOut.newPrompt("成绩类型不能为空");
        }

        if (objectionRecord.getReviewStat() == null) {
            return ApiOut.newPrompt("审核状态不能为空");
        }

        if (objectionRecord.getResumeId() == null) {
            return ApiOut.newPrompt("履历ID不能为空");
        }

        if (StringUtils.isBlank(objectionRecord.getReviewDesc())) {
            return ApiOut.newPrompt("审核描述不能为空");
        }

        objectionRecord.setReviewId(getUserId());
        objectionRecordService.update(INSTANCE.toModel(objectionRecord));
        return ApiOut.newSuccessResponse(true);
    }
}

package com.uptang.cloud.score.controller;

import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.converter.ShowConverter;
import com.uptang.cloud.score.common.model.Show;
import com.uptang.cloud.score.common.vo.ShowVO;
import com.uptang.cloud.score.feign.ShowProvider;
import com.uptang.cloud.score.service.IShowService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:02
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@RestController
@RequestMapping("/v1/show")
@Api(value = "ShowController", tags = {"公示数据管理"})
public class ShowController extends BaseController implements ShowProvider {

    private IShowService showService;

    public ShowController(IShowService showService) {
        this.showService = showService;
    }

    @PostMapping(path = "/{pageNum}/{pageSize}")
    @ApiOperation(value = "公示数据展示", response = ShowVO.class)
    @ApiParam(value = "传入json格式", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", paramType = "path", required = true)
    })
    public ApiOut<List<ShowVO>> page(@PathVariable Integer pageNum,
                                     @PathVariable Integer pageSize,
                                     @RequestBody ShowVO showVO) {

        Show show = ShowConverter.INSTANCE.toModel(showVO);
        Page<Show> shows = showService.page(pageNum, pageSize, show);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(shows, models -> {
            if (CollectionUtils.isEmpty(models)) {
                return Collections.emptyList();
            }
            return models.stream().map(ShowConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }
}




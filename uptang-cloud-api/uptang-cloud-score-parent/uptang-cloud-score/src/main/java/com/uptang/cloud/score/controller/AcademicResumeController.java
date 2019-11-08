package com.uptang.cloud.score.controller;

import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.converter.AcademicResumeConverter;
import com.uptang.cloud.score.common.converter.ResumeJoinScoreConverter;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.vo.AcademicResumeVO;
import com.uptang.cloud.score.common.vo.ResumeJoinScoreVO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import com.uptang.cloud.score.feign.AcademicResumeProvider;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping("/{pageNum}/{pageSize}")
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "履历", response = AcademicResumeVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", paramType = "path", required = true)
    })
    public ApiOut<List<AcademicResumeVO>> getList(@PathVariable Integer pageNum,
                                                  @PathVariable Integer pageSize,
                                                  @RequestBody AcademicResumeVO resumeVO) {

        AcademicResume resume = AcademicResumeConverter.INSTANCE.toModel(resumeVO);
        Page<AcademicResume> resumes = academicResumeService.page(pageNum, pageSize, resume);

        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(resumes, models -> {
            if (resumes == null || resumes.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(AcademicResumeConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @PutMapping(path = "/{id}")
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "更新履历", response = String.class)
    @ApiImplicitParam(name = "id", value = "成绩ID", paramType = "path", required = true)
    public ApiOut<String> update(@PathVariable Long id, @RequestBody AcademicResumeVO resumeVO) {
        resumeVO.setId(id);
        resumeVO.setUpdatedFounderId(getUserId());
        AcademicResume resume = AcademicResumeConverter.INSTANCE.toModel(resumeVO);
        boolean updated = academicResumeService.update(resume);

        String message = !updated ? "修改成绩失败" : "成绩修改成功";
        return ApiOut.newParameterRequiredResponse(message);
    }

}

package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.converter.AcademicResumeConverter;
import com.uptang.cloud.score.common.converter.ResumeJoinArchiveConverter;
import com.uptang.cloud.score.common.converter.ResumeJoinScoreConverter;
import com.uptang.cloud.score.common.converter.ScoreConverter;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.vo.AcademicResumeVO;
import com.uptang.cloud.score.common.vo.ResumeJoinArchiveVO;
import com.uptang.cloud.score.common.vo.ResumeJoinScoreVO;
import com.uptang.cloud.score.common.vo.ScoreVO;
import com.uptang.cloud.score.dto.ResumeJoinArchiveDTO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import com.uptang.cloud.score.feign.ScoreProvider;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    private final IAcademicResumeService academicResumeService;

    public ScoreController(IScoreService scoreService, IAcademicResumeService academicResumeService) {
        this.scoreService = scoreService;
        this.academicResumeService = academicResumeService;
    }

    @GetMapping(path = "/unfiled/{id}")
    @ApiOperation(value = "履历未归档分数详情-分数详情", response = AcademicResumeVO.class)
    @ApiImplicitParam(name = "id", value = "履历ID", paramType = "path", required = true)
    public ApiOut<ResumeJoinScoreVO> getUnfileDetail(@PathVariable Long id) {
        AcademicResume academicResume = new AcademicResume(id);
        final ResumeJoinScoreDTO resume = academicResumeService.getUnfileDetail(academicResume);
        return ApiOut.newSuccessResponse(ResumeJoinScoreConverter.INSTANCE.toVo(resume));
    }

    @GetMapping(path = "/archive/{id}")
    @ApiOperation(value = "履历归档分数详情-分数详情", response = AcademicResumeVO.class)
    @ApiImplicitParam(name = "id", value = "履历ID", paramType = "path", required = true)
    public ApiOut<ResumeJoinArchiveVO> getArchiveDetail(@PathVariable Long id) {
        AcademicResume academicResume = new AcademicResume(id);
        final ResumeJoinArchiveDTO resume = academicResumeService.getArchiveDetail(academicResume);
        return ApiOut.newSuccessResponse(ResumeJoinArchiveConverter.INSTANCE.toVo(resume));
    }

    @GetMapping("/archive/{pageNum}/{pageSize}")
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "归档履历", response = AcademicResumeVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", paramType = "path", required = true)
    })
    public ApiOut<List<ResumeJoinArchiveVO>> getArchiveList(@PathVariable Integer pageNum,
                                                            @PathVariable Integer pageSize,
                                                            @RequestBody AcademicResumeVO resumeVO) {

        AcademicResume resume = AcademicResumeConverter.INSTANCE.toModel(resumeVO);
        List<ResumeJoinArchiveDTO> resumes = academicResumeService.getArchiveList(pageNum, pageSize, resume);

        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(resumes, models -> {
            if (resumes == null || resumes.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(ResumeJoinArchiveConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }


    @GetMapping("/unfiled/{pageNum}/{pageSize}")
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "未归档履历", response = AcademicResumeVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", paramType = "path", required = true)
    })
    public ApiOut<List<ResumeJoinScoreVO>> getUnfiledList(@PathVariable Integer pageNum,
                                                          @PathVariable Integer pageSize,
                                                          @RequestBody AcademicResumeVO resumeVO) {

        AcademicResume resume = AcademicResumeConverter.INSTANCE.toModel(resumeVO);
        List<ResumeJoinScoreDTO> resumes = academicResumeService.getUnfiledList(pageNum, pageSize, resume);

        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(resumes, models -> {
            if (resumes == null || resumes.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(ResumeJoinScoreConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @PostMapping
    @ApiParam(value = "传入json格式", required = true)
    @ApiOperation(value = "成绩录入", response = String.class)
    public ApiOut<String> save(@RequestBody ResumeJoinScoreVO resumeJoinScore) {
        resumeJoinScore.setCreatedFounderId(getUserId());
        ResumeJoinScoreDTO resumeJoinScoreDTO = ResumeJoinScoreConverter.INSTANCE.toModel(resumeJoinScore);
//        boolean saved = scoreService.save(resumeJoinScoreDTO);
//        String message = !saved ? "成绩录入失败" : "成功录入成绩";
        return ApiOut.newPrompt("没实现");
    }

    @PutMapping(path = "/{id}/{subject}/{type}")
    @ApiOperation(value = "成绩修改", response = ScoreVO.class)
    @ApiParam(name = "成绩", value = "传入json格式", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "成绩ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "subject", value = "科目编码", paramType = "path", required = true),
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "scoreText", value = "等级(及格,不及格.)", paramType = "query"),
            @ApiImplicitParam(name = "scoreNumber", value = "数字分数", paramType = "query"),
    })
    public ApiOut<ScoreVO> update(@PathVariable("id") @NotNull Long id,
                                  @PathVariable("subject") @NotNull Integer subject,
                                  @PathVariable("type") @NotNull Integer type,
                                  @RequestParam(value = "scoreText", required = false) String scoreText,
                                  @RequestParam(value = "scoreNumber", required = false) Double scoreNumber) {
        final boolean noneScore = StringUtils.isBlank(scoreText) && scoreNumber == 0.0D || scoreNumber == null;
        Assert.isTrue(noneScore, "成绩不能为空");

        ScoreVO scoreVO = new ScoreVO();
        scoreVO.setId(id);
        scoreVO.setType(type);
        scoreVO.setSubject(subject);
        scoreVO.setScoreNumber(scoreNumber);
        scoreVO.setScoreText(scoreText);

        Score score = ScoreConverter.INSTANCE.toModel(scoreVO);
        boolean updated = scoreService.update().update(score);
        String message = !updated ? "修改成绩失败" : "成绩修改成功";
        return ApiOut.newPrompt(message);
    }
}

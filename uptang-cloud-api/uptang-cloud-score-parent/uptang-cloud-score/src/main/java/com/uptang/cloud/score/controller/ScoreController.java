package com.uptang.cloud.score.controller;

import com.uptang.cloud.score.common.converter.ArchiveScoreConverter;
import com.uptang.cloud.score.common.converter.ScoreConverter;
import com.uptang.cloud.score.common.converter.ShowScoreConverter;
import com.uptang.cloud.score.common.converter.SubjectConverter;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.vo.ArchiveScoreVO;
import com.uptang.cloud.score.common.vo.ScoreVO;
import com.uptang.cloud.score.common.vo.ShowScoreVO;
import com.uptang.cloud.score.common.vo.SubjectVO;
import com.uptang.cloud.score.dto.*;
import com.uptang.cloud.score.service.IArchiveScoreService;
import com.uptang.cloud.score.service.ISubjectService;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 18:55
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@RestController
@RequestMapping("/v1/score")
@Api(tags = "成绩管理")
public class ScoreController extends BaseController {

    private final ISubjectService subjectService;

    private final IArchiveScoreService archiveScoreService;

    public ScoreController(ISubjectService subjectService,
                           IArchiveScoreService archiveScoreService) {
        this.subjectService = subjectService;
        this.archiveScoreService = archiveScoreService;
    }

    @GetMapping("/show/{type}/{schoolId}/{gradeId}/{semesterId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", paramType = "query")
    })
    @ApiOperation(value = "查询公示数据", response = String.class)
    public ApiOut<List<ShowScoreVO>> show(@PathVariable("type") @NotNull Integer type,
                                          @PathVariable("gradeId") @NotNull Long gradeId,
                                          @PathVariable("schoolId") @NotNull Long schoolId,
                                          @PathVariable("semesterId") @NotNull Long semesterId,
                                          @RequestParam(value = "classId", required = false) Long classId,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        List<ShowScoreDTO> page = subjectService.show(schoolId, gradeId, classId, semesterId,
                ScoreTypeEnum.code(type), pageNum, pageSize);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(page, models -> {
            if (page == null || page.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(ShowScoreConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @GetMapping("/archive/{type}/{schoolId}/{gradeId}/{classId}/{semesterId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", paramType = "query")
    })
    @ApiOperation(value = "查询已归档数据", response = String.class)
    public ApiOut<List<ArchiveScoreVO>> archive(@PathVariable("type") @NotNull Integer type,
                                                @PathVariable("gradeId") @NotNull Long gradeId,
                                                @PathVariable("classId") Long classId,
                                                @PathVariable("schoolId") @NotNull Long schoolId,
                                                @PathVariable("semesterId") @NotNull Long semesterId,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        //Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type, Integer pageNum, Integer pageSize
        List<ArchiveScoreDTO> page = archiveScoreService.page(schoolId, gradeId, semesterId, classId,
                ScoreTypeEnum.code(type), pageNum, pageSize);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(page, models -> {
            if (page == null || page.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(ArchiveScoreConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @GetMapping("/unfiled/{type}/{schoolId}/{gradeId}/{classId}/{semesterId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩", paramType = "path", required = true),
            @ApiImplicitParam(name = "schoolId", value = "学校ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "gradeId", value = "年级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "classId", value = "班级ID", paramType = "path", required = true),
            @ApiImplicitParam(name = "semesterId", value = "学期ID", paramType = "path", required = true)
    })
    @ApiOperation(value = "查询未归档数据", response = String.class)
    public ApiOut<List<SubjectVO>> unfiled(@PathVariable("type") @NotNull Integer type,
                                           @PathVariable("gradeId") @NotNull Long gradeId,
                                           @PathVariable("classId") @NotNull Long classId,
                                           @PathVariable("schoolId") @NotNull Long schoolId,
                                           @PathVariable("semesterId") @NotNull Long semesterId,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<SubjectDTO> page = subjectService.page(schoolId, gradeId, classId, semesterId,
                ScoreTypeEnum.code(type), pageNum, pageSize);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(page, models -> {
            if (page == null || page.size() == 0) {
                return Collections.emptyList();
            }
            return models.stream().map(SubjectConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @PostMapping
    @ApiParam(value = "score object", required = true)
    @ApiOperation(value = "成绩录入", response = String.class)
    public ApiOut<String> addScore(@RequestBody ScoreVO score) {
        RequestParameter parameter = new RequestParameter();
        parameter.setToken(getToken());
        parameter.setUserId(getUserId());

        ScoreDTO scoreDto = ScoreConverter.toModel(score, parameter);
        return ApiOut.newPrompt(subjectService.addScore(scoreDto));
    }


    @PutMapping
    @ApiParam(value = "subject object", required = true)
    @ApiOperation(value = "修改成绩", response = String.class)
    public ApiOut<String> updateScore(@RequestBody SubjectVO subjectVO) {
        Double scoreNumber = subjectVO.getScoreNumber();
        Double scoreMinimum = 0.0D;
        if (scoreNumber < scoreMinimum) {
            return ApiOut.newPrompt("分数不能为负数");
        }

        RequestParameter parameter = new RequestParameter();
        parameter.setToken(getToken());
        parameter.setSchoolId(subjectVO.getSchoolId());
        parameter.setSemesterId(subjectVO.getSemesterId());
        parameter.setGradeId(subjectVO.getGradeId());
        parameter.setClassId(subjectVO.getClassId());
        parameter.setScoreType(subjectVO.getScoreType());
        Subject subject = SubjectConverter.INSTANCE.toModel(subjectVO);
        return ApiOut.newPrompt(subjectService.update(subject, parameter));
    }
}

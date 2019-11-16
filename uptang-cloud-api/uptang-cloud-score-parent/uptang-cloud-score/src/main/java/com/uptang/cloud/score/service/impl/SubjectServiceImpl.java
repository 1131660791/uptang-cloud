package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.*;
import com.uptang.cloud.score.repository.SubjectRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.service.ISubjectService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.ART;
import static com.uptang.cloud.score.common.enums.ScoreTypeEnum.HEALTH;
import static com.uptang.cloud.score.util.Collections.groupList;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectRepository, Subject> implements ISubjectService {

    @Autowired
    private IScoreStatusService statusService;

    @Autowired
    private IRestCallerService restCallerService;

    @Autowired
    private IAcademicResumeService academicResumeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchInsert(List<Subject> scores) {
        if (scores == null || scores.size() == 0) {
            return Collections.emptyList();
        }

        getBaseMapper().batchInsert(scores);
        return scores.stream().map(Subject::getId).collect(Collectors.toList());
    }

    @Override
    public String update(Subject score, RequestParameter parameter) {
        Assert.notNull(score, "请求参数不能为空");
        Assert.notNull(parameter, "请求参数不能为空");
        Assert.notNull(parameter.getToken(), "请求参数不能为空");
        String message = preCheck(parameter);
        if (message != null) {
            return message;
        }

        getBaseMapper().update(score);
        return "修改成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addScore(ScoreDTO score) {
        Assert.notNull(score, "请求参数不能为空");
        Assert.notNull(score.getParameter(), "请求参数不能为空");
        Assert.notNull(score.getResume(), "履历信息不能为空");
        Assert.notNull(score.getSubjects(), "科目成绩信息不能为空");

        RequestParameter parameter = score.getParameter();

        // 艺术类和体质健康类成绩一年只能导入一次
        @NotNull ScoreTypeEnum scoreType = parameter.getScoreType();
        if (scoreType == HEALTH || scoreType == ART) {
            AcademicResume resume = new AcademicResume();
            resume.setScoreType(scoreType);
            resume.setSchoolId(parameter.getSchoolId());
            resume.setGradeId(parameter.getGradeId());
            resume.setClassId(parameter.getClassId());
            resume.setSemesterId(parameter.getSemesterId());
            if (academicResumeService.importAgain(resume)) {
                throw new BusinessException(scoreType.getDesc() + "已录入");
            }
        }

        String message = preCheck(parameter);
        if (message != null) {
            return message;
        }

        AcademicResume resume = score.getResume();
        resume.setClassId(parameter.getUserId());
        Long id = academicResumeService.insert(resume);
        score.getSubjects().forEach(subject -> subject.setResumeId(id));
        batchInsert(score.getSubjects());
        return "成绩录入成功";
    }

    @Override
    public void batchDelete(List<Long> ids, ScoreTypeEnum scoreType) {
        if (ids != null && ids.size() > 0) {
            getBaseMapper().batchDelete(ids, scoreType);
        }
    }

    @Override
    public List<SubjectDTO> page(Long schoolId,
                                 Long gradeId,
                                 Long classId,
                                 Long semesterId,
                                 ScoreTypeEnum type,
                                 Integer pageNum,
                                 Integer pageSize) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");

        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return getBaseMapper().page(schoolId, gradeId, classId, semesterId, type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<List<Long>> batchInsert(Map<Integer, List<Subject>> groupList1) {
        List<List<Long>> ids = new ArrayList<>();
        Set<Map.Entry<Integer, List<Subject>>> entries = groupList1.entrySet();
        for (Map.Entry<Integer, List<Subject>> entry : entries) {
            ids.add(batchInsert(entry.getValue()));
        }
        return ids;
    }

    /**
     * 免测学生
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exemption(RequestParameter excel) {
        // 免测学生
        ExemptionDTO exemptionDto = new ExemptionDTO();
        exemptionDto.setToken(excel.getToken());
        exemptionDto.setClassId(excel.getClassId());
        exemptionDto.setGradeId(excel.getGradeId());
        List<AcademicResume> exemptions = restCallerService.exemption(exemptionDto);

        // 科目信息
        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setToken(excel.getToken());
        studentRequestDTO.setGradeId(excel.getGradeId());
        List<GradeCourseDTO> gradeCourse = restCallerService.gradeInfo(studentRequestDTO);
        if (exemptions != null && gradeCourse != null) {
            // 将学生信息插入
            exemptions.stream().forEach(course -> {
                course.setScoreType(HEALTH);
                course.setCreatorId(excel.getUserId());
                course.setCreatedTime(new Date());
            });

            Map<Long, Long> ids = academicResumeService.batchInsert(exemptions);

            List<GradeCourseDTO> courses = gradeCourse.stream()
                    .filter(course -> course.getScoreType() == HEALTH)
                    .collect(Collectors.toList());
            try {
                this.batchInsert(groupList(getSubjects(ids, courses)));
            } catch (Exception e) {
                List<Long> collect = ids.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
                academicResumeService.removeByIds(collect);
                getBaseMapper().batchDeleteResumeIDs(collect, HEALTH);
            }
        }
    }

    @Override
    public List<ShowScoreDTO> show(Long schoolId, Long gradeId, Long classId,
                                   Long semesterId, ScoreTypeEnum type,
                                   Integer pageNum, Integer pageSize) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return getBaseMapper().show(schoolId, gradeId, classId, semesterId, type);
    }

    /**
     * 构建科目
     *
     * @param ids     resume表ID
     * @param courses 科目
     * @return
     */
    private List<Subject> getSubjects(Map<Long, Long> ids, List<GradeCourseDTO> courses) {
        // 科目
        List<Subject> subjects = new ArrayList<>(courses.size());
        // resume ids
        Set<Map.Entry<Long, Long>> entries = ids.entrySet();
        // 免测学生人数
        for (Map.Entry<Long, Long> entry : entries) {
            Long resumeId = entry.getValue();
            // subjects 科目总数
            for (GradeCourseDTO course : courses) {
                Subject subject = new Subject();
                subject.setScoreType(HEALTH);
                subject.setScoreText(Strings.EMPTY);
                subject.setScoreNumber(Calculator.UNSIGNED_SMALLINT_MAX_VALUE);
                subject.setCode(course.getId());
                subject.setName(course.getSubjectName());
                subject.setResumeId(resumeId);
                subjects.add(subject);
            }
        }
        return subjects;
    }

    /**
     * 成绩录入前置校验
     *
     * @param parameter
     * @return
     */
    private String preCheck(RequestParameter parameter) {
        // 数据是否归档
        boolean archive = statusService.isArchive(parameter.getSchoolId(),
                parameter.getGradeId(),
                parameter.getSemesterId(),
                parameter.getScoreType());
        if (archive) {
            return "数据已归档";
        }

        // 权限校验
        RestRequestDTO restRequestDto = new RestRequestDTO();
        restRequestDto.setToken(parameter.getToken());
        boolean hasPromission = restCallerService.promissionCheck(restRequestDto);
        if (!hasPromission) {
            return "无权操作";
        }

        // 任务是否开放
        ModuleSwitchDTO moduleSwitch = ModuleSwitchDTO.builder().gradeId(parameter.getGradeId()).build();
        moduleSwitch.setToken(parameter.getToken());
        boolean isOpen = restCallerService.moduleSwitch(moduleSwitch);
        if (!isOpen) {
            return "任务未开放";
        }
        return null;
    }
}


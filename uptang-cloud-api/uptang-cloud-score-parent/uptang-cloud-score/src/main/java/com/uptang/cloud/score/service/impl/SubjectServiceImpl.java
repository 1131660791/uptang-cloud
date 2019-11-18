package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.*;
import com.uptang.cloud.score.repository.SubjectRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.service.ISubjectService;
import com.uptang.cloud.score.util.LocalAssert;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import org.apache.commons.lang3.StringUtils;
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
    private IScoreStatusService scoreStatusService;

    @Autowired
    private IAcademicResumeService academicResumeService;

    /**
     * 创建失败
     */
    private final String CREATE_FAILED = ResponseCodeEnum.CREATE_FAILED.getTemplate();

    private final String FAILED = ResponseCodeEnum.Failed.getTemplate();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchInsert(List<Subject> scores) {
        if (scores == null || scores.size() == 0) {
            return Collections.emptyList();
        }

        scores.forEach(score -> {
            if (StringUtils.isBlank(score.getScoreText())) {
                score.setScoreText(Strings.EMPTY);
            } else {
                score.setScoreNumber(Calculator.UNSIGNED_SMALLINT_MAX_VALUE);
            }
        });
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
        return ResponseCodeEnum.SUCCESS.getDesc();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addScore(ScoreDTO score) {
        Assert.notNull(score, "请求参数不能为空");

        AcademicResume resume = score.getResume();
        Assert.notNull(score.getSubjects(), "科目成绩信息不能为空");

        {
            AcademicResume queryArgs = new AcademicResume();
            queryArgs.setSchoolId(resume.getSchoolId());
            queryArgs.setGradeId(resume.getGradeId());
            queryArgs.setSemesterId(resume.getSemesterId());
            queryArgs.setClassId(resume.getClassId());
            ScoreTypeEnum scoreType = resume.getScoreType();
            queryArgs.setScoreType(scoreType);
            Long count = academicResumeService.exists(queryArgs);
            if (count != 0) {
                return String.format(FAILED, resume.getStudentName() + "的" + scoreType.getDesc() + "已经已录入");
            }
        }

        {
            RequestParameter parameter = score.getParameter();

            // 艺术类和体质健康类成绩一年只能导入一次
            @NotNull ScoreTypeEnum scoreType = parameter.getScoreType();
            if (scoreType == HEALTH || scoreType == ART) {
                if (academicResumeService.importAgain(parameter)) {
                    return String.format(FAILED, scoreType.getDesc() + "已录入");
                }
            }

            String message = preCheck(parameter);
            if (message != null) {
                return message;
            }
        }

        // 插入科目
        Long id = academicResumeService.insert(resume);
        score.getSubjects().forEach(subject -> {
            subject.setResumeId(id);
            ScoreTypeEnum type = subject.getScoreType();
            subject.setScoreType(type == null ? resume.getScoreType() : type);
        });

        try {
            // 插入科目
            batchInsert(score.getSubjects());
            // 添加状态
            scoreStatusService.insert(new ScoreStatus(id));
        } catch (Exception e) {
            academicResumeService.removeById(id);
            return String.format(CREATE_FAILED, "成绩录入");
        }

        return ResponseCodeEnum.SUCCESS.getDesc();
    }

    @Override
    public void batchDelete(List<Long> ids, ScoreTypeEnum scoreType) {
        if (ids != null && ids.size() > 0) {
            getBaseMapper().batchDelete(ids, scoreType);
        }
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

            // 履历表ID
            Map<Long, Long> ids = academicResumeService.batchInsert(exemptions);

            // 年级课程信息
            List<GradeCourseDTO> courses = gradeCourse.stream()
                    .filter(course -> course.getScoreType() == HEALTH)
                    .collect(Collectors.toList());

            try {
                // 插入科目
                this.batchInsert(groupList(getSubjects(ids, courses)));

                // 插入状态信息
                List<ScoreStatus> statuses = new ArrayList<>(ids.size());
                ids.forEach((studentId, resumeId) -> statuses.add(new ScoreStatus(resumeId)));
                scoreStatusService.batchInsert(statuses);
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
    public List<ShowScoreDTO> unfiled(Long schoolId, Long gradeId, Long classId,
                                      Long semesterId, ScoreTypeEnum type,
                                      Integer pageNum, Integer pageSize) {
        LocalAssert.mustCanNotBeEmpty(schoolId, gradeId, semesterId, type);

        AcademicResume resume = getAcademicResume(schoolId, gradeId, classId, semesterId, type);
        resume.setState(ScoreStatusEnum.SUBMIT);
        return query(pageNum, pageSize, resume);
    }

    @Override
    public List<ShowScoreDTO> show(Long schoolId, Long gradeId, Long classId,
                                   Long semesterId, ScoreTypeEnum type, Integer pageNum, Integer pageSize) {
        LocalAssert.mustCanNotBeEmpty(schoolId, gradeId, semesterId, type);

        AcademicResume resume = getAcademicResume(schoolId, gradeId, classId, semesterId, type);
        resume.setState(ScoreStatusEnum.SHOW);
        return query(pageNum, pageSize, resume);
    }


    @Override
    public List<Subject> queryByProperty(Subject sub) {
        if (sub == null || sub.getScoreType() == null) {
            return Collections.emptyList();
        }
        return getBaseMapper().queryByProperty(sub);
    }

    @Override
    public void batchDeleteResumeIDs(Map<Integer, List<Long>> resumeIds, ScoreTypeEnum scoreType) {
        if (resumeIds != null && scoreType != null) {
            Set<Map.Entry<Integer, List<Long>>> entries = resumeIds.entrySet();
            SubjectRepository subjectRepository = getBaseMapper();
            for (Map.Entry<Integer, List<Long>> entry : entries) {
                subjectRepository.batchDeleteResumeIDs(entry.getValue(), scoreType);
            }
        }
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
        boolean hasPromission = restCallerService.permissionCheck();
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

    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param resume
     * @return
     */
    private List<ShowScoreDTO> query(Integer pageNum, Integer pageSize, AcademicResume resume) {
        Page<ShowScoreDTO> query = academicResumeService.query(pageNum, pageSize, resume);

        if (query != null && query.size() > 0) {
            List<Long> resumeIds = query.stream().map(ShowScoreDTO::getId).collect(Collectors.toList());
            List<Subject> subjects = getBaseMapper().queryByResumeIds(resumeIds, resume.getScoreType());

            for (ShowScoreDTO score : query) {
                List<Subject> subjectList = new ArrayList<>();
                for (Subject subject : subjects) {
                    if (subject.getResumeId().compareTo(score.getId()) == 0) {
                        subjectList.add(subject);
                    }
                }
                score.setSubjects(subjectList);
            }
            return query;
        }

        return new Page();
    }

    private AcademicResume getAcademicResume(Long schoolId, Long gradeId, Long classId,
                                             Long semesterId, ScoreTypeEnum type) {
        AcademicResume resume = new AcademicResume();
        resume.setSchoolId(schoolId);
        resume.setGradeId(gradeId);
        resume.setClassId(classId);
        resume.setScoreType(type);
        resume.setSemesterId(semesterId);
        return resume;
    }
}


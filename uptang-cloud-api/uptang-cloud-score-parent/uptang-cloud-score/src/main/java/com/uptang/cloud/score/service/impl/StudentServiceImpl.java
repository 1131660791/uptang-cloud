package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.model.Student;
import com.uptang.cloud.score.dto.ExemptionDto;
import com.uptang.cloud.score.repository.StudentRepository;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.service.IStudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentRepository, Student> implements IStudentService {

    private final IRestCallerService restCallerService;

    public StudentServiceImpl(IRestCallerService restCallerService) {
        this.restCallerService = restCallerService;
    }

    @Override
    public List<Student> exoneration(String token, Long gradeId, Long classId) {
        if (StringUtils.isBlank(token)) {
            return Collections.emptyList();
        }

        List<String> exemption = restCallerService.exemption(new ExemptionDto(token, gradeId, classId));
        if (exemption == null || exemption.size() == 0) {
            return Collections.emptyList();
        }

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.in("student_code", exemption);
        return getBaseMapper().selectList(wrapper);
    }
}

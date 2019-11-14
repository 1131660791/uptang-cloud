package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.model.Student;
import com.uptang.cloud.score.repository.StudentRepository;
import com.uptang.cloud.score.service.IStudentService;
import org.springframework.stereotype.Service;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentRepository, Student> implements IStudentService {
}

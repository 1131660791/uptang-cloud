package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.model.Student;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IStudentService extends IService<Student> {

    /**
     * 体育成绩免测学生列表
     *
     * @param token
     * @param gradeId 年级ID
     * @param classId 班级ID
     * @return
     */
    List<Student> exoneration(String token, Long gradeId, Long classId);
}
package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.model.Student;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IStudentService extends IService<Student> {

    /**
     * 同步基础服务学生信息
     */
    void sync();

}
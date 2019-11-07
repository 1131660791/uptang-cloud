package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.AcademicResume;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IAcademicResumeService extends IService<AcademicResume> {

    /**
     * 更新履历
     *
     * @param resume
     * @return
     */
    boolean update(AcademicResume resume);


    /**
     * 履历列表
     *
     * @param pageNum  页码
     * @param pageSize 条数
     * @param resume   查询条件
     * @return
     */
    Page<AcademicResume> page(Integer pageNum, Integer pageSize, AcademicResume resume);
}
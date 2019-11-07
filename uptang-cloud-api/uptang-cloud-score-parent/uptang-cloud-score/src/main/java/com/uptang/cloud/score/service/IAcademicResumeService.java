package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ResumeJoinArchiveDTO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;

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

    /**
     * 获取归档详情
     *
     * @param academicResume 履历
     * @return
     */
    ResumeJoinArchiveDTO getArchiveDetail(AcademicResume academicResume);

    /**
     * 获取未归档详情
     *
     * @param academicResume 履历
     * @return
     */
    ResumeJoinScoreDTO getUnfileDetail(AcademicResume academicResume);
}
package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ResumeJoinArchiveDTO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Mapper
public interface AcademicResumeRepository extends BaseMapper<AcademicResume> {

    /**
     * 分页
     *
     * @param resume 查询参数
     * @return
     */
    Page<AcademicResume> page(AcademicResume resume);

    /**
     * 获取归档详情
     *
     * @param academicResume
     * @return
     */
    ResumeJoinArchiveDTO archiveDetail(AcademicResume academicResume);

    /**
     * 获取未归档详情
     *
     * @param academicResume
     * @return
     */
    ResumeJoinScoreDTO unfileDetail(AcademicResume academicResume);
}
package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.AcademicResume;
import org.springframework.stereotype.Repository;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Repository
public interface AcademicResumeRepository extends BaseMapper<AcademicResume> {

    /**
     * 分页
     *
     * @param resume 查询参数
     * @return
     */
    Page<AcademicResume> page(AcademicResume resume);
}
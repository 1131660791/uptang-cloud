package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ShowScoreDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

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
     * 批量插入
     *
     * @param resume
     */
    void batchInsert(List<AcademicResume> resume);

    /**
     * 插入
     *
     * @param resume
     */
    void save(AcademicResume resume);


    /**
     * 检查当前年级的成绩是否
     *
     * @param resume
     * @return
     */
    Date importAgain(AcademicResume resume);

    /**
     * 获取IDS
     *
     * @param resume
     * @return
     */
    List<AcademicResume> resume(AcademicResume resume);

    /**
     * @param resume
     * @return
     */
    Page<ShowScoreDTO> query(AcademicResume resume);

    /**
     * 是否存在
     *
     * @param resum
     * @return
     */
    Long exists(AcademicResume resum);
}
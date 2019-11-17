package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.ShowScoreDTO;

import java.util.List;
import java.util.Map;

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
     * 批量录入成绩
     *
     * @param groupMapList 批量录入数据
     * @return
     */
    List<Map<Long, Long>> batchSave(Map<Integer, List<AcademicResume>> groupMapList);

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
     * 批量插入
     *
     * @param resume
     * @return
     */
    Map<Long, Long> batchInsert(List<AcademicResume> resume);

    /**
     * 插入
     *
     * @param resume
     * @return
     */
    Long insert(AcademicResume resume);

    /**
     * 检查当前年级的成绩是否已经导入过了
     *
     * @param parameter web参数
     * @return true 已经导入过了 false 还未导入
     */
    boolean importAgain(RequestParameter parameter);

    /**
     * 获取Resume ids
     *
     * @param resume
     * @return
     */
    List<AcademicResume> resume(AcademicResume resume);

    /**
     *
     * @param resume
     * @return
     */
    Page<ShowScoreDTO> query(Integer pageNum, Integer pageSize, AcademicResume resume);

    /**
     * 是否存在
     *
     * @param resum
     * @return
     */
    Long exists(AcademicResume resum);
}
package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ObjectionRecord;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:34
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public interface IObjectionRecordService extends IService<ObjectionRecord> {

    /**
     * 新增
     *
     * @param objectionRecord
     */
    void add(ObjectionRecord objectionRecord);

    /**
     * 异议成绩
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    List<Long> resumeIds(Long schoolId,
                         Long gradeId,
                         Long classId,
                         Long semesterId,
                         ScoreTypeEnum type);

    /**
     * 异议条数
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    Long count(Long schoolId,
               Long gradeId,
               Long classId,
               Long semesterId,
               ScoreTypeEnum type);


    /**
     * 异议详情
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    List<Integer> detail(
            Long schoolId,
            Long gradeId,
            Long classId,
            Long semesterId,
            ScoreTypeEnum type, Integer pageNum, Integer pageSize);

    /**
     * 审核
     *
     * @param toModel
     */
    void update(ObjectionRecord toModel);

    /**
     * 分页
     *
     * @param type     分数类型
     * @param resumeId 履历ID
     * @param pageNum  页码
     * @param pageSize 显示条数
     */
    List<ObjectionRecord> page(ScoreTypeEnum type, Long resumeId, Integer pageNum, Integer pageSize);
}

package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ScoreStatus;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 17:22
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */

public interface IScoreStatusService extends IService<ScoreStatus> {

    /**
     * 是否归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    boolean isArchive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type);

    /**
     * 归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    boolean archive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type);

    /**
     * 撤销归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    boolean undoArchive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type);

    /**
     * 公示
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    boolean show(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type);

    /**
     * 撤销公示
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    boolean cancel(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type);
}
package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Score;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IScoreService extends IService<Score> {

    /**
     * 批量插入并返回ID
     *
     * @param scores
     * @return ids
     */
    List<Long> batchInsert(List<Score> scores);

    /**
     * 回滚
     *
     * @param subjectIds 回滚IDs
     * @param scoreType  成绩类型
     * @param cacheKey   缓存Key
     * @param hashKey    Hash表Key
     */
    void rollback(Map<String, List<Long>> subjectIds, ScoreTypeEnum scoreType, String cacheKey, String hashKey);

    /**
     * 单条插入
     *
     * @param score
     * @return
     */
    Long insert(Score score);

    /**
     * 回滚
     *
     * @param scoreIds  回滚IDs
     * @param scoreType 成绩类型
     */
    void rollback(Map<Integer, List<Long>> scoreIds, ScoreTypeEnum scoreType);
}
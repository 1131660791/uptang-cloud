package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.score.common.model.Score;
import org.springframework.stereotype.Repository;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Repository
public interface ScoreRepository extends BaseMapper<Score> {


    /**
     * 插入
     *
     * @param score
     */
    void save(Score score);
}
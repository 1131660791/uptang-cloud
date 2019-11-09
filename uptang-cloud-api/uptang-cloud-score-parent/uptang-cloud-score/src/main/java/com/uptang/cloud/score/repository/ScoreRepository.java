package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Score;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Repository
public interface ScoreRepository extends BaseMapper<Score> {


    /**
     * 批量插入
     *
     * @param resume
     */
    void batchInsert(List<Score> resume);


    /**
     * 批量删除
     *
     * @param ids
     * @param scoreType
     */
    void batchDelete(@Param("ids") List<Long> ids, @Param("scoreType") ScoreTypeEnum scoreType);

    /**
     * 单条插入
     *
     * @param score
     */
    void save(Score score);
}
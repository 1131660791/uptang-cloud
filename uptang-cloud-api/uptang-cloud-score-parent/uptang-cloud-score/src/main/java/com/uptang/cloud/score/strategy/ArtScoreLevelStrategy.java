package com.uptang.cloud.score.strategy;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 14:34
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 等级划分：90分以上为优秀，75-89分为良好，60-74分为合格，60分以下为不合格。
 */
public interface ArtScoreLevelStrategy {

    /**
     * 根据分数获取对等级
     *
     * @param score
     * @return
     */
    String level(Integer score);
}

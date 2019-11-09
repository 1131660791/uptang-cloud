package com.uptang.cloud.score.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 14:22
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 成绩类型 0 学业成绩 academic_score 1 体质健康 health_score 2 艺术成绩 art_score
 * <p>
 * 暂时先将表名写死 后续再考虑其他可行方案
 * FIXME 使用本路由时SQL条件必须带有type这一字段，前提是你配置了路由字段。
 */
public class ScoreTypeRoutingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    /**
     * "分片"逻辑名字
     */
    private static final String LOGIC_TABLE_NAME = "logic_score";

    /**
     * 学业成绩表名
     */
    private static final String ACADEMIC_SCORE_TABLE_NAME = "academic_score";

    /**
     * 体质成绩表名
     */
    private static final String HEALTH_SCORE_TABLE_NAME = "health_score";

    /**
     * 艺术成绩表名
     */
    private static final String ART_SCORE_TABLE_NAME = "art_score";


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        if (LOGIC_TABLE_NAME.equals(shardingValue.getLogicTableName())) {
            switch (shardingValue.getValue()) {
                case 1:
                    return HEALTH_SCORE_TABLE_NAME;
                case 2:
                    return ART_SCORE_TABLE_NAME;
                default:
                    return ACADEMIC_SCORE_TABLE_NAME;
            }
        }
        return shardingValue.getLogicTableName();
    }
}

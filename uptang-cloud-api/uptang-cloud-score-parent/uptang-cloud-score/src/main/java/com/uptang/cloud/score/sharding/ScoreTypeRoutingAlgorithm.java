package com.uptang.cloud.score.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 14:22
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 成绩类型 0 学业成绩 academic_score 1 体质健康 health_score 2 艺术成绩 art_score
 *
 * 暂时先将表名写死 后续再考虑其他可行方案
 */
public class ScoreTypeRoutingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    // "分片"逻辑名字
    private static final String LOGIC_TABLE_NAME = "logic_score";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        if (LOGIC_TABLE_NAME.equals(shardingValue.getLogicTableName())) {
            switch (shardingValue.getValue()) {
                case 0:
                    return "academic_score";
                case 1:
                    return "health_score";
                case 2:
                    return "art_score";
            }
        }
        return null;
    }
}

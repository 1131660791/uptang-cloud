package com.uptang.cloud.score.strategy;

import com.uptang.cloud.score.common.enums.LevelEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 14:38
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ArtScoreLevelStrategyFactory {

    private static final Map<LevelEnum, ArtScoreLevelStrategy> services = new ConcurrentHashMap<>(3);

    public static ArtScoreLevelStrategy getStrategy(LevelEnum levelEnum) {
        return services.get(levelEnum);
    }

    public static void register(LevelEnum levelEnum, ArtScoreLevelStrategy service) {
        services.put(levelEnum, service);
    }
}

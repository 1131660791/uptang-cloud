package com.uptang.cloud.score.strategy;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 14:38
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 简单实现
 */
public class ExcelProcessorStrategyFactory implements ApplicationListener<ContextClosedEvent> {

    private static final Map<ScoreTypeEnum, ExcelProcessorStrategy> STRATEGYS
            = new ConcurrentHashMap<>(3);

    public static ExcelProcessorStrategy getStrategy(ScoreTypeEnum scoreTypeEnum) {
        return STRATEGYS.get(scoreTypeEnum);
    }

    public static void register(ScoreTypeEnum scoreTypeEnum, ExcelProcessorStrategy strategy) {
        STRATEGYS.put(scoreTypeEnum, strategy);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        STRATEGYS.clear();
    }
}

package com.uptang.cloud.score.strategy;

import com.uptang.cloud.score.common.enums.LevelEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 14:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 60-74分为合格
 */
@Component
public class EligibilityStrategy implements ArtScoreLevelStrategy, InitializingBean {

    @Override
    public String level(Integer score) {
        return (score >= 60 && score <= 74) ? "合格" : null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ArtScoreLevelStrategyFactory.register(LevelEnum.QUALIFIED, this);
    }
}



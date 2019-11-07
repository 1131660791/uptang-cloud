package com.uptang.cloud.score.strategy;

import com.uptang.cloud.score.common.enums.LevelEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 14:33
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 90分以上
 */
@Component
public class ExcellentStrategy implements ArtScoreLevelStrategy, InitializingBean {

    @Override
    public String level(Integer score) {
        return score >= 90 ? "优" : null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ArtScoreLevelStrategyFactory.register(LevelEnum.BEST, this);
    }
}

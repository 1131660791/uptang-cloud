package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.dto.ArtScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;
import com.uptang.cloud.score.service.IRestCallerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 18:02
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Component
public class ArtScoreExcelProcessorStrategy implements ExcelProcessorStrategy<ArtScoreDTO>, InitializingBean {


    @Autowired
    private IRestCallerService restCallerService;

    @Override
    public void headMap(Map<Integer, String> headMap) {
        log.info("art header map ==> {}", headMap);
    }

    @Override
    public boolean check(ArtScoreDTO rawData, AnalysisContext context, ImportFromExcelDTO excel) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelProcessorStrategyFactory.register(ScoreTypeEnum.ART, this);
    }
}

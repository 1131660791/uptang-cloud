package com.uptang.cloud.score.template;

import com.alibaba.excel.event.AnalysisEventListener;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.dto.RequestParameter;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 21:18
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public class ListenerFactory {

    /**
     * @param excel
     */
    public static AnalysisEventListener newListener(RequestParameter excel) {
        switch (excel.getScoreType()) {
            case HEALTH:
                return new HealthAnalysisEventListener(excel);
            case ART:
                return new ArtAnalysisEventListener(excel);
            case ACADEMIC:
                return new AcademicAnalysisEventListener(excel);
            default:
                throw new BusinessException("成绩类型不匹配");
        }
    }
}

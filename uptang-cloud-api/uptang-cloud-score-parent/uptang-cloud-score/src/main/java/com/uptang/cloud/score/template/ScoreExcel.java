package com.uptang.cloud.score.template;

import com.alibaba.excel.context.AnalysisContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 8:52
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Component
public class ScoreExcel extends ExcelTemplate {

    @Override
    public void headMap(Map<Integer, String> headMap) {
        System.out.println("headMap ==> " + headMap);
    }

    @Override
    public boolean check(Object rawData, AnalysisContext context) {
        return false;
    }

    @Override
    public void post(List<Object> data) {
        System.out.println("data ==> " + data);
    }
}
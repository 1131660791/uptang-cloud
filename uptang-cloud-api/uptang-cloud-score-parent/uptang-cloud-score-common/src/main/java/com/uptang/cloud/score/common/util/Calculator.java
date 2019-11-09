package com.uptang.cloud.score.common.util;

import java.math.BigDecimal;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 15:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class Calculator {

    /**
     * 乘以10
     *
     * @param score 分数 【分数只保留一位小数位】
     * @return
     */
    public static Integer x10(Double score) {
        return new BigDecimal(score).multiply(new BigDecimal(10)).intValue();
    }

    /**
     * 除以10
     *
     * @param score 分数 【分数只保留一位小数位】
     * @return
     */
    public static Double dev10(Integer score) {
        return new BigDecimal(score).divide(new BigDecimal(10)).doubleValue();
    }
}

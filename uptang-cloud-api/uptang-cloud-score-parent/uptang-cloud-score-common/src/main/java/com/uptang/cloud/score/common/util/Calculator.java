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
     * 表示该分数值未录入，如果scoreText有值，则说明scoreNumber是没有值得
     * 数据库类型为 SMALLINT(5)
     * <p>
     * 数据库中使用SMALLINT(5) UNSIGNED整型来表示数值分数
     * 在实际场景中 分数是包含了0，所以用无符号SMALLINT最大值来表示【没有录入成绩】
     */
    public static final Integer UNSIGNED_SMALLINT_MAX_VALUE = 65535;

    /**
     * 乘以10
     *
     * @param score 分数 【分数只保留一位小数位】
     * @return
     */
    public static Integer x10(Double score) {
        BigDecimal scoreDecimal = new BigDecimal(score);
        if (scoreDecimal.compareTo(new BigDecimal(UNSIGNED_SMALLINT_MAX_VALUE)) == 0) {
            return UNSIGNED_SMALLINT_MAX_VALUE;
        }
        return scoreDecimal.multiply(new BigDecimal(10)).intValue();
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

    /**
     * 录入成绩为null时插入默认的为空标识 即五位数最大值
     *
     * @param score
     * @return
     */
    public static Integer defaultNumberScore(Double score) {
        if (score != null) {
            return Calculator.x10(score);
        }
        return UNSIGNED_SMALLINT_MAX_VALUE;
    }
}

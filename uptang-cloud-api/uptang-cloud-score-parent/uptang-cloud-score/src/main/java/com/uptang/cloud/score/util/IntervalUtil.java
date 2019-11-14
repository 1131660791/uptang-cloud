package com.uptang.cloud.score.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 15:59
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public class IntervalUtil {

    /**
     * 区间格式正则
     */
    private static final String REGEX = "^([<>≤≥\\[\\(]{1}(-?\\d+.?\\d*\\%?))$";

    /**
     * 判断number是否在interval区间范围内
     *
     * @param number   数值类型的
     * @param interval 正常的数学区间，包括无穷大等，如：(1,3)、>5%、(-∞,6]、(125%,135%)U(70%,80%)
     * @return true：表示data_value在区间interval范围内，
     * false：表示data_value不在区间interval范围内
     */
    public static boolean inInterval(String number, String interval) {
        //将区间和data_value转化为可计算的表达式
        String formula = getFormulaByAllInterval(number, interval, "||");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            //计算表达式
            return (Boolean) jse.eval(formula);
        } catch (Exception t) {
            return false;
        }
    }

    /**
     * 将所有阀值区间转化为公式：如
     * [75,80)   =》        number < 80 && date_value >= 75
     * (125%,135%)U(70%,80%)   =》 (date_value < 1.35 && date_value > 1.25) || (date_value < 0.8 && date_value > 0.7)
     *
     * @param number
     * @param interval  形式如：(125%,135%)U(70%,80%)
     * @param connector 连接符 如：") || ("
     */
    private static String getFormulaByAllInterval(String number, String interval, String connector) {
        StringBuffer buff = new StringBuffer();
        //如：（125%,135%）U (70%,80%)
        for (String limit : interval.split("U")) {
            buff.append("(")
                    .append(getFormulaByInterval(number, limit, " && "))
                    .append(")").append(connector);
        }

        String allLimitInvel = buff.toString();
        int index = allLimitInvel.lastIndexOf(connector);
        allLimitInvel = allLimitInvel.substring(0, index);
        return allLimitInvel;
    }

    /**
     * 将整个阀值区间转化为公式：如
     * 145)      =》         date_value < 145
     * [75,80)   =》        date_value < 80 && date_value >= 75
     *
     * @param date_value
     * @param interval   形式如：145)、[75,80)
     * @param connector  连接符 如：&&
     */
    private static String getFormulaByInterval(String date_value, String interval, String connector) {
        StringBuffer buff = new StringBuffer();
        //如：[75,80)、≥80
        String split = ",";
        for (String halfInterval : interval.split(split)) {
            buff.append(getFormulaByHalfInterval(halfInterval, date_value)).append(connector);
        }

        String limitInvel = buff.toString();
        int index = limitInvel.lastIndexOf(connector);
        limitInvel = limitInvel.substring(0, index);
        return limitInvel;
    }

    /**
     * 将半个阀值区间转化为公式：如
     * 145)      =》         date_value < 145
     * ≥80%      =》         date_value >= 0.8
     * [130      =》         date_value >= 130
     * <80%      =》         date_value < 0.8
     *
     * @param halfInterval 形式如：145)、≥80%、[130、<80%
     * @param date_value
     * @return date_value < 145
     */
    private static String getFormulaByHalfInterval(String halfInterval, String date_value) {
        halfInterval = halfInterval.trim();
        //包含无穷大则不需要公式
        if (halfInterval.contains("∞")) {
            return "1 == 1";
        }

        StringBuffer formula = new StringBuffer();
        String data, opera;

        //表示判断方向（如>）在前面 如：≥80%
        if (halfInterval.matches(REGEX)) {
            opera = halfInterval.substring(0, 1);
            data = halfInterval.substring(1);
        } else {
            //[130、145)
            opera = halfInterval.substring(halfInterval.length() - 1);
            data = halfInterval.substring(0, halfInterval.length() - 1);
        }

        double value = dealPercent(data);
        formula.append(date_value).append(" ").append(opera).append(" ").append(value);
        String a = formula.toString();
        //转化特定字符
        return a.replace("[", ">=")
                .replace("(", ">")
                .replace("]", "<=")
                .replace(")", "<")
                .replace("≤", "<=")
                .replace("≥", ">=");
    }

    /**
     * 去除百分号，转为小数
     *
     * @param str 可能含百分号的数字
     * @return
     */
    private static double dealPercent(String str) {
        double d;
        String percentageSign = "%";
        if (str.contains(percentageSign)) {
            str = str.substring(0, str.length() - 1);
            d = Double.parseDouble(str) / 100;
        } else {
            d = Double.parseDouble(str);
        }
        return d;
    }
}
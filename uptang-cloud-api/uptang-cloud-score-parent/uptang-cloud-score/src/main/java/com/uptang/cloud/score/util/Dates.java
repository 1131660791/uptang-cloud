package com.uptang.cloud.score.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-14 14:04
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public class Dates {

    private static final String[] patterns
            = new String[]{"yyyyMMdd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-M-dd HH:mm:ss"};

    /**
     * 时间字符串格式化
     *
     * @param stringDate
     * @return
     */
    public static Date formatter(String stringDate) {
        int index = 0;
        boolean formatter;
        do {
            try {
                LocalDate localDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(patterns[index]));
                return java.sql.Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            } catch (Exception e) {
                formatter = true;
            }
            index++;
        } while (formatter && index < patterns.length);
        return null;
    }
}

package com.uptang.cloud.score.util;

import java.util.regex.Pattern;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-09 19:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class Numbers {

    private static final Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");

    /**
     * 是否为浮点数？double或float类型。
     *
     * @param str 传入的字符串。
     * @return 是浮点数返回true, 否则返回false。
     */
    public static boolean isFloat(String str) {
        return pattern.matcher(str).matches();
    }
}

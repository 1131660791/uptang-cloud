package com.uptang.cloud.starter.common.util;

import java.util.regex.Pattern;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class Validator {
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^(1[3456789])\\d{9}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\S]*)?";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 校验手机号
     *
     * @param mobile 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return StringUtils.isNotBlank(mobile) && Pattern.matches(REGEX_MOBILE, StringUtils.trimToEmpty(mobile));
    }

    /**
     * 校验邮箱
     *
     * @param email 电子邮箱
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && Pattern.matches(REGEX_EMAIL, StringUtils.trimToEmpty(email));
    }

    /**
     * 校验汉字
     *
     * @param chinese 汉字
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return StringUtils.isNotBlank(chinese) && Pattern.matches(REGEX_CHINESE, StringUtils.trimToEmpty(chinese));
    }

    /**
     * 校验URL
     *
     * @param url URL
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
}
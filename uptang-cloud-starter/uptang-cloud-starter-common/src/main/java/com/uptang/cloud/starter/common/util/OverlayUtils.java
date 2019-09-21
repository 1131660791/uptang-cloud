package com.uptang.cloud.starter.common.util;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public final class OverlayUtils {
    private static final String OVERLAY_SEPARATORS = "****";

    /**
     * 将手机号中间4位变成 *
     *
     * @param mobile 手机号
     * @return 变化后的手机号
     */
    public static String getOverlaidMobile(String mobile) {
        return Validator.isMobile(mobile)
                ? StringUtils.substring(mobile, 0, 3) + OVERLAY_SEPARATORS + StringUtils.substring(mobile, 7)
                : StringUtils.trimToEmpty(mobile);
    }

    /**
     * 对电子邮箱进行隐私保护处理
     *
     * @param email 电子邮箱
     * @return 变化后的电子邮箱
     */
    public static String getOverlaidEmail(String email) {
        return Validator.isEmail(email)
                ? StringUtils.overlay(email, OVERLAY_SEPARATORS, 2, email.indexOf('@'))
                : StringUtils.trimToEmpty(email);
    }
}

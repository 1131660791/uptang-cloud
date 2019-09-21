package com.uptang.cloud.starter.common;

import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public interface Constants {
    /**
     * 系统启动时间
     */
    Date SYSTEM_START_TIME = new Date();

    /**
     * 系统默认编码
     */
    String DEFAULT_CHAR_SET = "UTF-8";

    /**
     * 日期格式
     */
    String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 日期时间格式
     */
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Token 在传递过程中(Session, Cookie, URL, Header 参数)的名字
     */
    String TOKEN_PARA_NAME = "token";

    /**
     * 调试标志
     */
    String PARA_DEBUG = "debug";
}

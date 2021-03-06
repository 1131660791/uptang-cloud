package com.uptang.cloud.core;

import java.nio.charset.Charset;
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
    String DEFAULT_CHAR_SET_VALUE = "UTF-8";
    Charset DEFAULT_CHAR_SET = Charset.forName(DEFAULT_CHAR_SET_VALUE);

    /**
     * 日期格式
     */
    String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 紧凑的日期格式
     */
    String COMPACT_DATE_FORMAT = "yyyyMMdd";

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

package com.uptang.cloud.score.util;

import org.springframework.context.ApplicationContext;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 13:20
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void set(ApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
}

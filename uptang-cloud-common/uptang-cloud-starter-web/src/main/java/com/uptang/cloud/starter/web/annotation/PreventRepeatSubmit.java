package com.uptang.cloud.starter.web.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventRepeatSubmit {
    /**
     * 不允许重复提交的时间间隔， 时间单位：秒
     */
    @AliasFor("timeout")
    int value() default 0;

    @AliasFor("value")
    int timeout() default 0;

    /**
     * 生成缓存的前缀
     *
     * @return 锁前缀
     */
    String prefix() default "";
}

package com.uptang.cloud.starter.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authenticate {
    /**
     * 需要的权限字符
     */
    String[] value() default {};

    /**
     * 认证策略
     */
    AuthPolicy policy() default AuthPolicy.REQUIRED;
}




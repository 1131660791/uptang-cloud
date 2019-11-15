package com.uptang.cloud.score.annotation;

import java.lang.annotation.*;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 12:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 标记注解 检查任务是否开启
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JobSwitchCheck {}

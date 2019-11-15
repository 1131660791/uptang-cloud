package com.uptang.cloud.score.annotation;

import java.lang.annotation.*;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 15:02
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 是否归档检查 标记注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ArchiveCheck {
}

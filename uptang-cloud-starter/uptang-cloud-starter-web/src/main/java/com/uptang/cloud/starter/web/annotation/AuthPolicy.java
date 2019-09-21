package com.uptang.cloud.starter.web.annotation;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public enum AuthPolicy {
    /**
     * 必需要有权限码
     */
    REQUIRED,

    /**
     * 忽略权限检查
     */
    IGNORED
}

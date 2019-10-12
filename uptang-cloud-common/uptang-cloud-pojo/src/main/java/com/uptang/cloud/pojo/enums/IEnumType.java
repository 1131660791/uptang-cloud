package com.uptang.cloud.pojo.enums;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public interface IEnumType {
    /**
     * 数据库中定义的 数字 状态码
     *
     * @return 数字状态
     */
    int getCode();

    /**
     * 简单描述
     *
     * @return 简单描述
     */
    String getDesc();
}

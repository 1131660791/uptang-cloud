package com.uptang.cloud.starter.web.util;

import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@FunctionalInterface
public interface ViewObjectConverter<M, V> {

    /**
     * 批量将 Models 转换成 VOs
     *
     * @param models 数据库中查出的对象
     * @return 用于页面显示的对象（VO）
     */
    List<V> toVos(List<M> models);
}

package com.uptang.cloud.starter.web.util;

import com.github.pagehelper.Page;
import com.uptang.cloud.starter.common.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class PageableEntitiesConverter {
    /**
     * 将模 model 转换成 vo
     *
     * @param models    数据库中的实体
     * @param converter 数据转换器
     * @return 根据实体转出来的VO
     */
    public static <M, V> List<V> toVos(List<M> models, ViewObjectConverter<M, V> converter) {
        if (null == converter) {
            throw new BusinessException("没有传入实体数据转换器");
        }

        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>(0);
        }

        // 根据自定义逻辑对数据进行转换
        List<V> vos = converter.toVos(models);

        // 如果使用了数据库的自动页页，将分页信息复制出来
        if (models instanceof Page) {
            Page page = (Page) models;

            Page<V> pageVos = new Page<>();
            pageVos.setTotal(page.getTotal());
            pageVos.setPageSize(page.getPageSize());
            pageVos.setPageNum(page.getPageNum());
            pageVos.addAll(vos);
            return pageVos;
        }
        return vos;
    }

    /**
     * 将Repository获取的结果转化为Page对象,一般用于解决不允许联表查询的情况，需要多表的值
     *
     * @param models Repository获取的结果
     * @return Page对象
     */
    public static <V> Page<V> toPageEntities(List<V> models) {
        Page<V> results = new Page<>();
        if (models instanceof Page) {
            Page page = (Page) models;
            results.setTotal(page.getTotal());
            results.setPageSize(page.getPageSize());
            results.setPageNum(page.getPageNum());
        }
        return results;
    }
}

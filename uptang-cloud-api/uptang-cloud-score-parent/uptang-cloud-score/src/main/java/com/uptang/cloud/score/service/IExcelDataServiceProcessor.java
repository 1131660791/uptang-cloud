package com.uptang.cloud.score.service;

import com.uptang.cloud.score.dto.RequestParameter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:37
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public interface IExcelDataServiceProcessor {

    /**
     * 处理Excel数据
     *
     * @param parameter
     * @return
     */
    void processor(RequestParameter parameter);
}

package com.uptang.cloud.score.service;

import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.Show;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public interface IShowService {


    /**
     * 公示数据展示
     *
     * @param pageNum  页码
     * @param pageSize 条数
     * @param show     查询条件
     * @return
     */
    Page<Show> page(Integer pageNum, Integer pageSize, Show show);
}

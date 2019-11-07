package com.uptang.cloud.score.repository;

import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.model.Show;
import org.springframework.stereotype.Repository;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:08
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Repository
public interface ShowRepository {

    /**
     * 分页
     *
     * @param show
     * @return
     */
    Page<Show> page(Show show);
}
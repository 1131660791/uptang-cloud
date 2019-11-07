package com.uptang.cloud.score.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.model.Show;
import com.uptang.cloud.score.repository.ShowRepository;
import com.uptang.cloud.score.service.IShowService;
import org.springframework.stereotype.Service;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Service
public class ShowServiceImpl implements IShowService {

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Page<Show> page(Integer pageNum, Integer pageSize, Show show) {
        PageHelper.startPage(pageNum, pageSize);
        return showRepository.page(show);
    }
}
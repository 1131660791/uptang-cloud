package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.score.common.model.Review;
import org.springframework.stereotype.Repository;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Repository
public interface ReviewRepository extends BaseMapper<Review> {

    Review queryByProperty(Review review);

    /**
     * 查公示期内的数据
     *
     * @param review
     * @return
     */
    Review queryShowDate(Review review);
}
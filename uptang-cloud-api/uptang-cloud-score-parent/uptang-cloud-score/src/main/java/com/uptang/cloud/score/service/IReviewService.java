package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.model.Review;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IReviewService extends IService<Review> {


    /**
     * 异议提交
     *
     * @param review
     * @return
     */
    boolean submit(Review review);

    /**
     * 异议审核
     *
     * @param review
     * @return
     */
    boolean verify(Review review);
}
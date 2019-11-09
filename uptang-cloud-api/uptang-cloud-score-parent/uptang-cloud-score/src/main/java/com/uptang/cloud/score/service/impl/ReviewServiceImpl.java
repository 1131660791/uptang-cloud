package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.score.common.enums.ArchiveEnum;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.model.Review;
import com.uptang.cloud.score.repository.ReviewRepository;
import com.uptang.cloud.score.service.IReviewService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class ReviewServiceImpl extends ServiceImpl<ReviewRepository, Review>
        implements IReviewService {

    @Override
    public boolean submit(Review review) {
        Review review1 = getBaseMapper().queryShowDate(review);
        if (review1 == null || !review1.getShowStat().isShow()) {
            throw new BusinessException("该数据不在公示期内,无法操作!");
        }

        review1.setObjectionDesc(review.getObjectionDesc());
        review1.setObjection(ObjectionEnum.SHOW);
        review1.setArchive(ArchiveEnum.SUBMIT);
        review1.setCreatedTime(new Date());
        int raw = getBaseMapper().updateById(review1);
        return raw >= 1 ? true : false;
    }


    @Override
    public boolean verify(Review review) {
        if (NumberUtils.isNotPositive(review.getId())) {
            return false;
        }

        Review updateReview = new Review();
        updateReview.setObjection(ObjectionEnum.NONE);
        updateReview.setUpdatedTime(new Date());
        int raw = getBaseMapper().updateById(updateReview);
        return raw >= 1 ? true : false;
    }
}

package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IScoreService extends IService<Score> {

    Score getDetail(Long id,Integer type);

    /**
     * 录入成绩
     *
     * @param score
     * @param resume
     * @return
     */
    boolean save(Score score, AcademicResume resume);
}
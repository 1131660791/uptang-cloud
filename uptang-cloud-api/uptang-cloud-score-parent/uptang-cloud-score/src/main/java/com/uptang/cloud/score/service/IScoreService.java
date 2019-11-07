package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IScoreService extends IService<Score> {

    /**
     * 录入成绩
     *
     * @param resumeJoinScore
     * @return
     */
    boolean save(ResumeJoinScoreDTO resumeJoinScore);
}
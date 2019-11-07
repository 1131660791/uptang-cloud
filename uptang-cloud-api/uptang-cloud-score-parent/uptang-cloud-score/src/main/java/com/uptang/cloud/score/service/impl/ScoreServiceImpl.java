package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.repository.ScoreRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import com.uptang.cloud.score.service.IScoreService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreRepository, Score> implements IScoreService {

    private final IAcademicResumeService academicResumeService;

    public ScoreServiceImpl(IAcademicResumeService academicResumeService) {
        this.academicResumeService = academicResumeService;
    }

    @Override
    public boolean save(Score score, AcademicResume resume) {
        return false;
    }


    @Override
    public Score getDetail(Long id, Integer type) {
        Map<String, Object> param = new TreeMap<>();
        param.put("id", id);
        param.put("type", type);
        return getBaseMapper().selectByMap(param).get(0);
    }
}


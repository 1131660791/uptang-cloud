package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.repository.ScoreRepository;
import com.uptang.cloud.score.service.IScoreService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreRepository, Score> implements IScoreService {

    private final StringRedisTemplate redisTemplate;

    public ScoreServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> batchInsert(List<Score> scores) {
        if (scores == null || scores.size() == 0) {
            return Collections.emptyList();
        }

        getBaseMapper().batchInsert(scores);
        return scores.stream().map(Score::getId).collect(Collectors.toList());
    }

    @Override
    public void rollback(Map<String, List<Long>> subjectIds, ScoreTypeEnum scoreType, String cacheKey, String hashKey) {
        Set<Map.Entry<String, List<Long>>> entries = subjectIds.entrySet();
        for (Map.Entry<String, List<Long>> group : entries) {
            getBaseMapper().batchDelete(group.getValue(), scoreType);
        }

        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(cacheKey, hashKey);
    }
}


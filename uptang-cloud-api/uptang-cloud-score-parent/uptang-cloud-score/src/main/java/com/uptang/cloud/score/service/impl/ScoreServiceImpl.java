package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.repository.ScoreRepository;
import com.uptang.cloud.score.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Slf4j
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreRepository, Score> implements ScoreService {

}

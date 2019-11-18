package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ArchiveScore;
import com.uptang.cloud.score.dto.ArchiveScoreDTO;
import com.uptang.cloud.score.repository.ArchiveScoreRepository;
import com.uptang.cloud.score.service.IArchiveScoreService;
import com.uptang.cloud.score.util.LocalAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class ArchiveScoreServiceImpl
        extends ServiceImpl<ArchiveScoreRepository, ArchiveScore>
        implements IArchiveScoreService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<List<Long>> batchInsert(Map<Integer, List<ArchiveScore>> archiveScores) {
        if (archiveScores != null && archiveScores.size() > 0) {
            Set<Map.Entry<Integer, List<ArchiveScore>>> entries = archiveScores.entrySet();
            ArchiveScoreRepository archiveRepository = getBaseMapper();

            List<List<Long>> ids = new ArrayList<>();
            for (Map.Entry<Integer, List<ArchiveScore>> entry : entries) {
                List<ArchiveScore> value = entry.getValue();
                archiveRepository.batchInsert(value);
                ids.add(value.stream().map(ArchiveScore::getId).collect(Collectors.toList()));
            }
            return ids;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean batchDelete(List<Long> resumeIds) {
        if (resumeIds != null && resumeIds.size() > 0) {
            getBaseMapper().batchDelete(resumeIds);
        }
        return false;
    }

    @Override
    public List<ArchiveScoreDTO> page(Long schoolId,
                                      Long gradeId,
                                      Long classId,
                                      Long semesterId,
                                      ScoreTypeEnum type,
                                      Integer pageNum,
                                      Integer pageSize) {

        LocalAssert.mustCanNotBeEmpty(schoolId, gradeId, semesterId, type);

        PageHelper.startPage(pageNum, pageSize);
        return getBaseMapper().page(schoolId, gradeId, classId, semesterId, type);
    }
}

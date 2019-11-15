package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ArchiveScore;
import com.uptang.cloud.score.dto.ArchiveScoreDTO;
import com.uptang.cloud.score.repository.ArchiveScoreRepository;
import com.uptang.cloud.score.service.IArchiveScoreService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

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
    public List<ArchiveScore> batchInsert(List<ArchiveScore> archiveScores) {
        if (archiveScores != null && archiveScores.size() > 0) {
            getBaseMapper().batchInsert(archiveScores);
            return archiveScores;
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
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return getBaseMapper().page(schoolId, gradeId, classId, semesterId, type);
    }
}

package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.ResumeJoinArchiveDTO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import com.uptang.cloud.score.repository.AcademicResumeRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class AcademicResumeServiceImpl extends ServiceImpl<AcademicResumeRepository, AcademicResume>
        implements IAcademicResumeService {

    @Override
    public boolean update(AcademicResume academicResume) {
        academicResume.setUpdatedTime(new Date());
        return Optional
                .ofNullable(getBaseMapper().selectById(academicResume.getId()))
                .filter(Objects::nonNull)
                .map(resume -> this.getBaseMapper().updateById(academicResume))
                .flatMap(raw -> raw >= 1 ? Optional.of(true) : Optional.of(false))
                .orElse(false);
    }

    /**
     * if (scoreGroupList != null && scoreGroupList.size() > 0) {
     * final List<List<Long>> subjectIds = new ArrayList<>();
     * Set<Map.Entry<Integer, List<List<Score>>>> subjectGroup = scoreGroupList.entrySet();
     * for (Map.Entry<Integer, List<List<Score>>> subjects : subjectGroup) {
     * List<List<Score>> value = subjects.getValue();
     * for (List<Score> scores : value) {
     * subjectIds.add(scoreService.batchInsert(scores));
     * }
     * }
     * System.out.println(subjectIds);
     * }
     * <p>
     * subjectIds.stream().forEach(ids -> academicResumes.stream().forEach(resume -> resume.setScoreIds(ids)));
     * subjectIds.clear();
     *
     * @param groupMapList 批量录入数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSave(Map<Integer, List<AcademicResume>> groupMapList) {
        final Set<Map.Entry<Integer, List<AcademicResume>>> entries = groupMapList.entrySet();
        for (Map.Entry<Integer, List<AcademicResume>> entry : entries) {
            batchInsert(entry.getValue());
        }
    }

    @Override
    public Page<AcademicResume> page(Integer pageNum, Integer pageSize, AcademicResume resume) {
        PageHelper.startPage(pageNum, pageSize);
        return getBaseMapper().page(resume);
    }

    @Override
    public ResumeJoinArchiveDTO getArchiveDetail(AcademicResume academicResume) {
        return getBaseMapper().archiveDetail(academicResume);
    }

    @Override
    public ResumeJoinScoreDTO getUnfileDetail(AcademicResume academicResume) {
        return getBaseMapper().unfileDetail(academicResume);
    }

    @Override
    public List<ResumeJoinArchiveDTO> getArchiveList(Integer pageNum, Integer pageSize,
                                                     AcademicResume academicResume) {
        PageHelper.startPage(pageNum, pageSize);
        return getBaseMapper().archiveList(academicResume);
    }

    @Override
    public List<ResumeJoinScoreDTO> getUnfiledList(Integer pageNum, Integer pageSize,
                                                   AcademicResume academicResume) {
        PageHelper.startPage(pageNum, pageSize);
        return getBaseMapper().unfileList(academicResume);
    }

    @Override
    public void batchInsert(List<AcademicResume> resume) {
        Optional.ofNullable(resume)
                .filter(list -> (list != null || !list.isEmpty()))
                .ifPresent(getBaseMapper()::batchInsert);

    }
}

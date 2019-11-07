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

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Service
public class AcademicResumeServiceImpl extends ServiceImpl<AcademicResumeRepository, AcademicResume> implements IAcademicResumeService {

    @Override
    public boolean update(AcademicResume academicResume) {
        academicResume.setUpdatedTime(new Date());
        return Optional
                .ofNullable(getBaseMapper().selectById(academicResume.getId()))
                .filter(Objects::nonNull)
                .map(resume -> getBaseMapper().updateById(academicResume))
                .flatMap(raw -> raw >= 1 ? Optional.of(true) : Optional.of(false))
                .orElse(false);
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
}

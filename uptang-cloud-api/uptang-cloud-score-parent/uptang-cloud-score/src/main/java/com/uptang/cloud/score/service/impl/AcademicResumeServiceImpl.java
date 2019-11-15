package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.repository.AcademicResumeRepository;
import com.uptang.cloud.score.service.IAcademicResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
        academicResume.setModifiedTime(new Date());
        return Optional
                .ofNullable(getBaseMapper().selectById(academicResume.getId()))
                .filter(Objects::nonNull)
                .map(resume -> this.getBaseMapper().updateById(academicResume))
                .flatMap(raw -> raw >= 1 ? Optional.of(true) : Optional.of(false))
                .orElse(false);
    }

    /**
     * 批次插入
     *
     * @param groupMapList 批量录入数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Map<Long, Long>> batchSave(Map<Integer, List<AcademicResume>> groupMapList) {
        if (groupMapList != null && groupMapList.size() > 0) {
            List<Map<Long, Long>> ids = new ArrayList<>();
            final Set<Map.Entry<Integer, List<AcademicResume>>> entries = groupMapList.entrySet();
            for (Map.Entry<Integer, List<AcademicResume>> entry : entries) {
                ids.add(batchInsert(entry.getValue()));
            }
            return ids;
        }
        return Collections.emptyList();
    }

    @Override
    public Page<AcademicResume> page(Integer pageNum, Integer pageSize, AcademicResume resume) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return getBaseMapper().page(resume);
    }

    @Override
    public Map<Long, Long> batchInsert(List<AcademicResume> resume) {
        if (resume != null && resume.size() > 0) {
            getBaseMapper().batchInsert(resume);
            return resume.stream().collect(Collectors.toMap(AcademicResume::getStudentId, AcademicResume::getId));
        }

        return Collections.emptyMap();
    }

    @Override
    public Long insert(AcademicResume resume) {
        Assert.notNull(resume, "请求参数不能为空");
        Assert.notNull(resume.getScoreType(), "成绩类型不能为空");

        resume.setCreatedTime(new Date());
        Optional.ofNullable(resume).ifPresent(getBaseMapper()::save);
        return resume.getId();
    }


    @Override
    public boolean importAgain(AcademicResume resume) {
        // false 还未导入
        Assert.notNull(resume, "请求参数不能为空");
        Date date = getBaseMapper().importAgain(resume);
        if (date == null) {
            return false;
        }
        // Unable to obtain Year from TemporalAccessor: xxxxxxx of type java.time.Instant
        Year year = Year.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (year.compareTo(Year.now()) == 0) {
            return true;
        }

        // true 已经导入过了
        return true;
    }

    @Override
    public List<AcademicResume> getResumeIds(AcademicResume resume) {
        Assert.notNull(resume, "请求参数不能为空");
        Assert.notNull(resume.getScoreType(), "成绩类型不能为空");
        return getBaseMapper().getResumeIds(resume);
    }
}

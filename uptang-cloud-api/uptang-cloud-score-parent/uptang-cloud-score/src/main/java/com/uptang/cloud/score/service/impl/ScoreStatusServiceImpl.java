package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.repository.ScoreStatusRepository;
import com.uptang.cloud.score.service.IScoreStatusService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 17:23
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Service
public class ScoreStatusServiceImpl
        extends ServiceImpl<ScoreStatusRepository, ScoreStatus>
        implements IScoreStatusService {

    @Override
    public boolean isArchive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        return getBaseMapper().isArchive(schoolId, gradeId, semesterId, type) > 0;
    }

    @Override
    public boolean archive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        getBaseMapper().archive(schoolId, gradeId, semesterId, type);
        return true;
    }

    @Override
    public boolean undoArchive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        getBaseMapper().undoArchive(schoolId, gradeId, semesterId, type);
        return true;
    }

    @Override
    public boolean show(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        getBaseMapper().show(schoolId, gradeId, semesterId, type);
        return true;
    }

    @Override
    public boolean cancel(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        getBaseMapper().cancel(schoolId, gradeId, semesterId, type);
        return true;
    }
}

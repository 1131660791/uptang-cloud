package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ObjectionRecord;
import com.uptang.cloud.score.common.model.ObjectionRecordResume;
import com.uptang.cloud.score.repository.ObjectionRecordRepository;
import com.uptang.cloud.score.service.IObjectionRecordService;
import com.uptang.cloud.score.service.IScoreStatusService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:36
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Service
public class ObjectionRecordServiceImpl
        extends ServiceImpl<ObjectionRecordRepository, ObjectionRecord>
        implements IObjectionRecordService {

    private final IScoreStatusService statusService;

    public ObjectionRecordServiceImpl(IScoreStatusService statusService) {
        this.statusService = statusService;
    }

    @Override
    public String add(ObjectionRecord record) {
        if (record != null
                || record.getResumeId() == null
                || record.getScoreType() == null) {
            // 公示状态检查
            Long count = statusService.checkState(record.getResumeId());
            if (count != null && count != 0) {
                ObjectionRecordRepository baseMapper = getBaseMapper();
                // 异议记录是否已经存在
                Long records = baseMapper.exists(record.getResumeId(),
                        record.getScoreType(),
                        record.getCreatorId());
                // 同一个人只能提交一次异议
                if (records != null && records.compareTo(0L) == 0) {
                    record.setCreatedTime(new Date());
                    record.setReviewStat(ReviewEnum.NONE);
                    record.setReviewDesc(Strings.EMPTY);
                    record.setState(ObjectionEnum.PROCESS);
                    record.setReviewDesc(Strings.EMPTY);
                    baseMapper.add(record);
                }
                return "已接收您的意见";
            }
            return "该成绩不在公示期内";
        }
        return "请求数据不能为空";
    }

    @Override
    public List<Long> resumeIds(Long schoolId, Long gradeId, Long classId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        return getBaseMapper().resumeIds(schoolId, gradeId, classId, semesterId, type);
    }

    @Override
    public Long count(Long schoolId, Long gradeId, Long classId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        return getBaseMapper().count(schoolId, gradeId, classId, semesterId, type);
    }

    @Override
    public void verify(ObjectionRecord toModel) {
        toModel.setModifiedTime(new Date());
        getBaseMapper().verify(toModel);
    }


    @Override
    public List<ObjectionRecordResume> page(Long schoolId,
                                            Long gradeId,
                                            Long classId,
                                            Long semesterId,
                                            ScoreTypeEnum type,
                                            Integer pageNum,
                                            Integer pageSize) {
        Assert.notNull(schoolId, "学校不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");

        PageHelper.startPage(pageNum, pageSize);
        return getBaseMapper().page(schoolId, gradeId, classId, semesterId, type);
    }

    @Override
    public List<Long> records(ScoreTypeEnum type, List<Long> resumeIds) {
        if (type == null || resumeIds == null || resumeIds.size() == 0) {
            return Collections.emptyList();
        }
        return getBaseMapper().records(type, resumeIds);
    }
}

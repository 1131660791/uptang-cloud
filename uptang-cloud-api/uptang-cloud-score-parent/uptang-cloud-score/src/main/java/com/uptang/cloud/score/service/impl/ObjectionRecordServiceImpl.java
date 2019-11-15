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
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    @Override
    public void add(ObjectionRecord objectionRecord) {
        if (objectionRecord != null) {
            objectionRecord.setCreatedTime(new Date());
            objectionRecord.setReviewStat(ReviewEnum.NONE);
            objectionRecord.setReviewDesc(Strings.EMPTY);
            objectionRecord.setState(ObjectionEnum.PROCESS);
            getBaseMapper().add(objectionRecord);
        }
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
    public void update(ObjectionRecord toModel) {
        toModel.setModifiedTime(new Date());
        getBaseMapper().update(toModel);
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
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return getBaseMapper().page(schoolId, gradeId, classId, semesterId, type);
    }
}

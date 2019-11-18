package com.uptang.cloud.score.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ArchiveScore;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.ShowScoreDTO;
import com.uptang.cloud.score.repository.ScoreStatusRepository;
import com.uptang.cloud.score.service.IArchiveScoreService;
import com.uptang.cloud.score.service.IObjectionRecordService;
import com.uptang.cloud.score.service.IScoreStatusService;
import com.uptang.cloud.score.service.ISubjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static com.uptang.cloud.score.util.Collections.groupList;

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

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IArchiveScoreService archiveScoreService;

    @Autowired
    private IObjectionRecordService objectionRecordService;

    @Override
    public boolean isArchive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");
        return getBaseMapper().isArchive(schoolId, gradeId, semesterId, type) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean archive(Long schoolId, Long gradeId, Long semesterId, ScoreTypeEnum type) {
        Assert.notNull(schoolId, "学校ID不能为空");
        Assert.notNull(gradeId, "年级ID不能为空");
        Assert.notNull(semesterId, "学期ID不能为空");
        Assert.notNull(type, "成绩类型不能为空");

        final Integer pageNum = 1;
        final Integer pageSize = 100;
        List<ArchiveScore> statuses = new ArrayList<>();
        {
            Page<ShowScoreDTO> scoreDTOS = (Page<ShowScoreDTO>)
                    subjectService.show(schoolId, gradeId, null, semesterId, type, pageNum, pageSize);
            if (scoreDTOS == null || scoreDTOS.size() == 0) {
                return false;
            }

            buildArchiveData(scoreDTOS, statuses);

            // 分页数据 scoreDTOS.getPages() 总页数
            for (int i = 2; i <= scoreDTOS.getPages(); i++) {
                scoreDTOS = (Page<ShowScoreDTO>)
                        subjectService.show(schoolId, gradeId, null, semesterId, type, i, pageSize);
                buildArchiveData(scoreDTOS, statuses);
            }
        }

        List<Long> resumeIds = statuses.stream().map(ArchiveScore::getResumeId).collect(Collectors.toList());
        {
            // 排除异议数据
            List<Long> objectionRecords = objectionRecordService.records(type, resumeIds);
            Iterator<Long> iterator = resumeIds.iterator();
            Iterator<ArchiveScore> scoreIterator = statuses.iterator();
            for (Long record : objectionRecords) {
                while (iterator.hasNext()) {
                    if (iterator.next().compareTo(record) == 0) {
                        iterator.remove();
                    }
                }

                while (scoreIterator.hasNext()) {
                    ArchiveScore next = scoreIterator.next();
                    if (next.getResumeId().compareTo(record) == 0) {
                        scoreIterator.remove();
                    }
                }
            }
        }

        if (!resumeIds.isEmpty()) {
            // 归档
            archiveScoreService.batchInsert(groupList(statuses));

            // 更新状态
            Map<Integer, List<Long>> integerListMap = groupList(resumeIds);
            Set<Map.Entry<Integer, List<Long>>> entries = integerListMap.entrySet();
            ScoreStatusRepository baseMapper = getBaseMapper();
            for (Map.Entry<Integer, List<Long>> entry : entries) {
                baseMapper.archive(ScoreStatusEnum.ARCHIVE, entry.getValue());
            }

            // 删除科目表
            subjectService.batchDeleteResumeIDs(integerListMap, type);
        }
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

    @Override
    public List<ScoreStatus> batchInsert(List<ScoreStatus> lists) {
        if (lists == null || lists.size() == 0) {
            return Collections.emptyList();
        }

        lists.stream().forEach(status -> {
            status.setStartedTime(new Date());
            status.setState(ScoreStatusEnum.SUBMIT);
        });
        getBaseMapper().batchInsert(lists);
        return lists;
    }

    @Override
    public Long insert(ScoreStatus status) {
        if (status == null || status.getResumeId() == null) {
            return null;
        }

        status.setState(ScoreStatusEnum.SUBMIT);
        status.setStartedTime(new Date());
        getBaseMapper().save(status);
        return status.getId();
    }

    @Override
    public Long checkState(Long resumeId) {
        Assert.notNull(resumeId, "履历ID不能为空");
        return getBaseMapper().checkState(resumeId);
    }

    /**
     * 构建归档数据
     *
     * @param scoreDTOS 待归档数据
     * @param statuses  归档数据集合
     */
    private void buildArchiveData(Page<ShowScoreDTO> scoreDTOS, List<ArchiveScore> statuses) {
        for (ShowScoreDTO score : scoreDTOS) {
            StringBuilder stringBuilder = new StringBuilder("{");
            List<Subject> subjects = score.getSubjects();
            for (Subject subject : subjects) {
                if (StringUtils.isBlank(subject.getScoreText())) {
                    stringBuilder.append(toJSon(subject.getName(), subject.getScoreNumber()));
                } else {
                    stringBuilder.append(toJSon(subject.getName(), subject.getScoreText()));
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).append("}");

            ArchiveScore scoreStatus = new ArchiveScore();
            scoreStatus.setResumeId(score.getId());
            scoreStatus.setType(score.getScoreType());
            scoreStatus.setDetail(stringBuilder.toString());
            statuses.add(scoreStatus);
        }
    }


    private String toJSon(String subject, Object value) {
        if (Integer.class.isAssignableFrom(value.getClass())) {
            return "\"" + subject + "\":" + Calculator.dev10(Integer.parseInt(value + "")) + ",";
        }
        return "\"" + subject + "\":\"" + value + "\",";
    }
}

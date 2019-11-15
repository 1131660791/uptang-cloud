package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ArchiveScore;
import com.uptang.cloud.score.dto.ArchiveScoreDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface IArchiveScoreService extends IService<ArchiveScore> {

    /**
     * 批量插入
     *
     * @param archiveScores
     */
    List<ArchiveScore> batchInsert(List<ArchiveScore> archiveScores);

    /**
     * 根据履历ID批量删除
     *
     * @param resumeIds 履历ID
     */
    boolean batchDelete(@Param("resumeIds") List<Long> resumeIds);

    /**
     * 分页
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param classId    班级
     * @param semesterId 学期
     * @param type       成绩类型
     * @param pageNum    页码
     * @param pageSize   显示条数
     * @return
     */
    List<ArchiveScoreDTO> page(Long schoolId,
                               Long gradeId,
                               Long classId,
                               Long semesterId,
                               ScoreTypeEnum type,
                               Integer pageNum, Integer pageSize);
}

package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ArchiveScore;
import com.uptang.cloud.score.dto.ArchiveScoreDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Repository
public interface ArchiveScoreRepository extends BaseMapper<ArchiveScore> {


    /**
     * 批量插入
     *
     * @param archiveScores
     */
    void batchInsert(List<ArchiveScore> archiveScores);

    /**
     * 根据履历ID批量删除
     *
     * @param resumeIds 履历ID
     */
    void batchDelete(@Param("resumeIds") List<Long> resumeIds);


    /**
     * 分页
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param classId    班级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    Page<ArchiveScoreDTO> page(@Param("schoolId") Long schoolId,
                               @Param("gradeId") Long gradeId,
                               @Param("classId") Long classId,
                               @Param("semesterId") Long semesterId,
                               @Param("type") ScoreTypeEnum type);
}
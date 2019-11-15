package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.dto.ShowScoreDTO;
import com.uptang.cloud.score.dto.SubjectDTO;
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
public interface SubjectRepository extends BaseMapper<Subject> {


    /**
     * 批量插入
     *
     * @param resume
     */
    void batchInsert(List<Subject> resume);


    /**
     * 批量删除
     *
     * @param ids
     * @param scoreType
     */
    void batchDelete(@Param("ids") List<Long> ids,
                     @Param("scoreType") ScoreTypeEnum scoreType);

    /**
     * 修改成绩
     *
     * @param score
     */
    void update(Subject score);

    /**
     * 分页
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    Page<SubjectDTO> page(@Param("schoolId") Long schoolId,
                          @Param("gradeId") Long gradeId,
                          @Param("classId") Long classId,
                          @Param("semesterId") Long semesterId,
                          @Param("type") ScoreTypeEnum type);

    /**
     * 根据ResumeID进行删除
     *
     * @param resumeIds
     * @param scoreType
     */
    void batchDeleteResumeIDs(@Param("resumeIds") List<Long> resumeIds,
                              @Param("scoreType") ScoreTypeEnum scoreType);


    /**
     * 公示数据
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param classId    班级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @return
     */
    Page<ShowScoreDTO> show(@Param("schoolId") Long schoolId,
                            @Param("gradeId") Long gradeId,
                            @Param("classId") Long classId,
                            @Param("semesterId") Long semesterId,
                            @Param("type") ScoreTypeEnum type);
}
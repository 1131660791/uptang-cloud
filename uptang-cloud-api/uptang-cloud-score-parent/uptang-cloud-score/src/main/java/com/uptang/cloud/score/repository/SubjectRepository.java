package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
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
     * 根据ResumeID进行删除
     *
     * @param resumeIds
     * @param scoreType
     */
    void batchDeleteResumeIDs(@Param("resumeIds") List<Long> resumeIds,
                              @Param("scoreType") ScoreTypeEnum scoreType);


    /**
     * 分页
     *
     * @param sub
     * @return
     */
    List<Subject> queryByProperty(Subject sub);


    /**
     * @param resumeIds
     * @param scoreType
     * @return
     */
    List<Subject> queryByResumeIds(@Param("resumeIds") List<Long> resumeIds,
                                   @Param("scoreType") ScoreTypeEnum scoreType);
}
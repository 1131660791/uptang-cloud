package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ScoreStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-12 08:18:10
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Mapper
public interface ScoreStatusRepository extends BaseMapper<ScoreStatus> {

    /**
     * 根据属性查询
     *
     * @param status
     * @return
     */
    ScoreStatus queryByProperty(ScoreStatus status);

    /**
     * 根据ID更新
     *
     * @param status
     */
    void update(ScoreStatus status);

    /**
     * 是否归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    Long isArchive(@Param("schoolId") Long schoolId,
                   @Param("gradeId") Long gradeId,
                   @Param("semesterId") Long semesterId,
                   @Param("type") ScoreTypeEnum type);

    /**
     * 归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    void archive(@Param("schoolId") Long schoolId,
                 @Param("gradeId") Long gradeId,
                 @Param("semesterId") Long semesterId,
                 @Param("type") ScoreTypeEnum type);

    /**
     * 撤销归档
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    void undoArchive(@Param("schoolId") Long schoolId,
                     @Param("gradeId") Long gradeId,
                     @Param("semesterId") Long semesterId,
                     @Param("type") ScoreTypeEnum type);

    /**
     * 公示
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     */
    void show(@Param("schoolId") Long schoolId,
              @Param("gradeId") Long gradeId,
              @Param("semesterId") Long semesterId,
              @Param("type") ScoreTypeEnum type);

    /**
     * 撤销公示
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     */
    void cancel(@Param("schoolId") Long schoolId,
                @Param("gradeId") Long gradeId,
                @Param("semesterId") Long semesterId,
                @Param("type") ScoreTypeEnum type);
}
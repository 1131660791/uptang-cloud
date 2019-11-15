package com.uptang.cloud.score.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ObjectionRecord;
import com.uptang.cloud.score.common.model.ObjectionRecordResume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 8:31
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ObjectionRecordRepository extends BaseMapper<ObjectionRecord> {

    /**
     * 根据属性查
     *
     * @param objectionRecord
     * @return
     */
    ObjectionRecord queryByProperty(ObjectionRecord objectionRecord);

    /**
     * 根据主键更新
     *
     * @param objectionRecord
     */
    void update(ObjectionRecord objectionRecord);

    /**
     * 新增
     *
     * @param objectionRecord
     */
    void add(ObjectionRecord objectionRecord);


    /**
     * 异议成绩
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    List<Long> resumeIds(@Param("schoolId") Long schoolId,
                         @Param("gradeId") Long gradeId,
                         @Param("classId") Long classId,
                         @Param("semesterId") Long semesterId,
                         @Param("type") ScoreTypeEnum type);

    /**
     * 异议条数
     *
     * @param schoolId   学校
     * @param gradeId    年级
     * @param semesterId 学期
     * @param type       成绩类型
     * @return
     */
    Long count(@Param("schoolId") Long schoolId,
               @Param("gradeId") Long gradeId,
               @Param("classId") Long classId,
               @Param("semesterId") Long semesterId,
               @Param("type") ScoreTypeEnum type);

    /**
     * 分页
     *
     * @param schoolId
     * @param gradeId
     * @param classId
     * @param semesterId
     * @return
     */
    Page<ObjectionRecordResume> page(@Param("schoolId") Long schoolId,
                                     @Param("gradeId") Long gradeId,
                                     @Param("classId") Long classId,
                                     @Param("semesterId") Long semesterId,
                                     @Param("type") ScoreTypeEnum type);
}

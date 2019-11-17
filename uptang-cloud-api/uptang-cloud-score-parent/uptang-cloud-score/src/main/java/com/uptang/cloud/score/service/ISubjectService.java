package com.uptang.cloud.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.ScoreDTO;
import com.uptang.cloud.score.dto.ShowScoreDTO;

import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
public interface ISubjectService extends IService<Subject> {

    /**
     * 批量插入并返回ID
     *
     * @param scores
     * @return ids
     */
    List<Long> batchInsert(List<Subject> scores);


    /**
     * 查询未归档数据
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
    List<ShowScoreDTO> unfiled(Long schoolId,
                               Long gradeId,
                               Long classId,
                               Long semesterId,
                               ScoreTypeEnum type,
                               Integer pageNum, Integer pageSize);

    /**
     * 批量新增
     *
     * @param groupList1
     * @return 批量返回新增ID
     */
    List<List<Long>> batchInsert(Map<Integer, List<Subject>> groupList1);

    /**
     * 修改成绩
     *
     * @param score
     * @param parameter
     * @return 信息 message
     */
    String update(Subject score, RequestParameter parameter);

    /**
     * 录入成绩
     *
     * @param score
     * @return
     */
    String addScore(ScoreDTO score);

    /**
     * 根据ResumeID批量删除
     *
     * @param ids
     * @param scoreType
     */
    void batchDelete(List<Long> ids, ScoreTypeEnum scoreType);

    /**
     * 插入免测学生成绩
     *
     * @param excel
     */
    void exemption(RequestParameter excel);

    /**
     * 公式数据
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param classId    班级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     * @param pageNum    页码
     * @param pageSize   每页显示行数
     * @return
     */
    List<ShowScoreDTO> show(Long schoolId,
                            Long gradeId,
                            Long classId,
                            Long semesterId,
                            ScoreTypeEnum type,
                            Integer pageNum,
                            Integer pageSize);

    /**
     * 分页
     *
     * @param sub
     * @return
     */
    List<Subject> queryByProperty(Subject sub);

    /**
     * 根据ResumeID进行删除
     *
     * @param resumeIds
     * @param scoreType
     */
    void batchDeleteResumeIDs(Map<Integer,List<Long>> resumeIds, ScoreTypeEnum scoreType);
}


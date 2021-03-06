package com.uptang.cloud.score.service;

import com.uptang.cloud.score.common.enums.PublicityTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.*;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 调用远程服务
 */
public interface IRestCallerService {


    /**
     * 任务是否开放接口
     *
     * @param moduleSwitchDto 请求参数
     * @return
     */
    boolean moduleSwitch(ModuleSwitchDTO moduleSwitchDto);


    /**
     * 学生信息查询接口
     *
     * @param studentRequest
     * @return
     */
    StuListDTO studentList(StudentRequestDTO studentRequest);

    /**
     * FIXME 暂时写在这里
     * 当前用户权限校验接口
     *
     * @return 是否有权限
     */
    boolean permissionCheck();

    /**
     * 年级课程信息
     *
     * @param studentRequest
     * @return
     */
    List<GradeCourseDTO> gradeInfo(StudentRequestDTO studentRequest);


    /**
     * 免测学生列表
     *
     * @param exemptionDto
     * @return 学籍号列表
     */
    List<AcademicResume> exemption(ExemptionDTO exemptionDto);

    /**
     * 公示时间接口
     *
     * @param token 用户登录后Token信息
     * @param type  模块类型编号
     * @return
     */
    PublicityDTO publicity(String token, PublicityTypeEnum type);
}


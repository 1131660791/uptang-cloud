package com.uptang.cloud.score.util;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-16 15:16
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public class LocalAssert {

    /**
     * 非空参数校验
     *
     * @param schoolId   学校ID
     * @param gradeId    年级ID
     * @param semesterId 学期ID
     * @param type       成绩类型
     */
    public static void mustCanNotBeEmpty(Long schoolId,
                                         Long gradeId,
                                         Long semesterId,
                                         ScoreTypeEnum type) {
        org.springframework.util.Assert.notNull(schoolId, "学校ID不能为空");
        org.springframework.util.Assert.notNull(gradeId, "年级ID不能为空");
        org.springframework.util.Assert.notNull(semesterId, "学期ID不能为空");
        org.springframework.util.Assert.notNull(type, "成绩类型不能为空");
    }
}

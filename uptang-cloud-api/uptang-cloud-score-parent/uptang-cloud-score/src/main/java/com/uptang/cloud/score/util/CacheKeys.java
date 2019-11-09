package com.uptang.cloud.score.util;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Slf4j
public class CacheKeys extends com.uptang.cloud.core.util.CacheKeys {

    /**
     * 缓存当前录入的科目IDs
     *
     * @param schoolID  学校ID
     * @param gradeId   年级ID
     * @param scoreType 成绩类型
     * @param userID    当前用户ID
     * @return
     */
    public static String subjectIdsCacheKey(Long schoolID, Long gradeId,
                                            Integer scoreType, Long userID) {
        return getKey("subject", schoolID, gradeId, scoreType, userID);
    }

    /**
     * 缓存当年学校下年级学生
     *
     * @param schoolId 学校ID
     * @param gradeId  年级ID
     * @return
     */
    public static String scoreStudetInfoList(Long schoolId, Long gradeId) {
        return getKey("subject", "student", schoolId + "", gradeId + "");
    }
}

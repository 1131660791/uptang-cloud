package com.uptang.cloud.score.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 * <p>
 * 模块类型编号,
 * 1.日常表现
 * 2.综合实践活动
 * 3.成就奖励
 * 4.学业成绩
 * 5.体质健康测评
 * 6.标志性卡
 * 7.学期评价
 * 8.毕业评价
 */
@Getter
@AllArgsConstructor
public enum PublicityTypeEnum {

    /**
     * 日常表现
     */
    DAILY_PERFORMANCE(1),

    /**
     * 综合实践活动
     */
    COMPREHENSIVE_PRACTICE(2),

    /**
     * 成就奖励
     */
    ACHIEVEMENT_REWARD(3),

    /**
     * 学业成绩
     */
    ACADEMIC_ACHIEVEMENT(4),

    /**
     * 体质健康测评
     */
    PHYSICAL_HEALTH_ASSESSMENT(5),

    /**
     * 标志性卡
     */
    ICONIC_CARD(6),

    /**
     * 学期评价
     */
    SEMESTER_EVALUATION(7),

    /**
     * 毕业评价
     */
    GRADUATION_EVALUATION(8),

    /**
     * 未识别
     */
    UNKNOWN(98);

    private final int code;

    @JsonCreator
    public static PublicityTypeEnum code(int code) {
        for (PublicityTypeEnum member : PublicityTypeEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return PublicityTypeEnum.UNKNOWN;
    }

    @JsonValue
    public int toValue() {
        for (PublicityTypeEnum member : PublicityTypeEnum.values()) {
            if (member.getCode() == this.getCode()) {
                return member.getCode();
            }
        }
        return UNKNOWN.code;
    }
}

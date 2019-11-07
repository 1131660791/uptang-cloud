package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:40
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
 */
@Getter
@AllArgsConstructor
public enum ScoreTypeEnum implements IEnumType {

    /**
     * 学业成绩
     */
    ACADEMIC(0, "学业成绩"),

    /**
     * 体质健康成绩
     */
    HEALTH(1, "体质健康成绩"),

    /**
     * 艺术成绩
     */
    ART(2, "艺术成绩");

    @EnumValue
    private final int code;

    private final String desc;

    public static ScoreTypeEnum code(int code) {
        for (ScoreTypeEnum member : ScoreTypeEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

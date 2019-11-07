package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:55
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 学期 0 上半学期 1 下班学期
 */
@Getter
@AllArgsConstructor
public enum SemesterEnum implements IEnumType {

    /**
     * 上半学期
     */
    LAST(0, "上半学期"),

    /**
     * 下半学期
     */
    NEXT(1, "下半学期");

    @EnumValue
    private final int code;

    private final String desc;

    public static SemesterEnum code(int code) {
        for (SemesterEnum member : SemesterEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

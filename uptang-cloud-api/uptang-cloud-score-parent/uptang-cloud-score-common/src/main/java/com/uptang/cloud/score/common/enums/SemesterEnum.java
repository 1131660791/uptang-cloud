package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
    NEXT(1, "下半学期"),


    UNKNOWN(9, "未识别");

    @EnumValue
    private final int code;

    private final String desc;

    @JsonCreator
    public static SemesterEnum code(int code) {
        for (SemesterEnum member : SemesterEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return SemesterEnum.UNKNOWN;
    }

    @JsonValue
    public String toValue() {
        for (SemesterEnum member : SemesterEnum.values()) {
            if (member.getDesc().equals(this.desc)) {
                return member.desc;
            }
        }
        return UNKNOWN.desc;
    }
}

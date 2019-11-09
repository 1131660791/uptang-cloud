package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:47
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 异议 0 无状态 1 异议处理期 2 DONE
 */
@Getter
@AllArgsConstructor
public enum ObjectionEnum implements IEnumType {

    /**
     * 无状态
     */
    NONE(0, "无状态"),

    /**
     * 异议处理期
     */
    SHOW(1, "异议处理期"),

    UNKNOWN(9, "未识别");

    @EnumValue
    private final int code;

    private final String desc;

    public static ObjectionEnum code(int code) {
        for (ObjectionEnum member : ObjectionEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return ObjectionEnum.UNKNOWN;
    }

    @JsonValue
    public int toValue() {
        for (ObjectionEnum member : ObjectionEnum.values()) {
            if (member.getCode() == this.getCode()) {
                return member.getCode();
            }
        }
        return UNKNOWN.code;
    }
}

package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-12 11:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 审核状态 1 通过 2 拒绝
 */
@Getter
@AllArgsConstructor
public enum ReviewEnum implements IEnumType {

    /**
     * 通过
     */
    PASS(1,"通过"),

    /**
     * 拒绝
     */
    REFUSE(2,"拒绝"),

    /**
     *
     */
    NONE(9,"none");

    @EnumValue
    private final int code;

    private final String desc;


    @JsonCreator
    public static ReviewEnum code(int code) {
        for (ReviewEnum member : ReviewEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        for (ReviewEnum member : ReviewEnum.values()) {
            if (member.desc.equals(this.desc)) {
                return member.desc;
            }
        }
        return "UNKNOWN";
    }
}

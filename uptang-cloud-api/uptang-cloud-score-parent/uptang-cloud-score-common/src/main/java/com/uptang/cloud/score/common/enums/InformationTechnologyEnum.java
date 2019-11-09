package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 11:09
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 信息技术，成绩为等级只有四个等级: 0 A ,1 B ,2 C ,3 D
 */
@Getter
@AllArgsConstructor
public enum InformationTechnologyEnum implements IEnumType {

    /**
     * 信息技术等级
     */
    A(0),

    /**
     * 信息技术等级
     */
    B(1),

    /**
     * 信息技术等级
     */
    C(2),

    /**
     * 信息技术等级
     */
    D(3),

    /**
     * 未识别
     */
    UNKNOWN(9);

    @EnumValue
    private final int code;

    @Override
    public String getDesc() {
        return name();
    }

    public static InformationTechnologyEnum level(String level) {
        if (InformationTechnologyEnum.A.name().equals(level)) {
            return InformationTechnologyEnum.A;
        }

        if (InformationTechnologyEnum.B.name().equals(level)) {
            return InformationTechnologyEnum.B;
        }

        if (InformationTechnologyEnum.C.name().equals(level)) {
            return InformationTechnologyEnum.C;
        }

        if (InformationTechnologyEnum.D.name().equals(level)) {
            return InformationTechnologyEnum.D;
        }

        return InformationTechnologyEnum.UNKNOWN;
    }

    @JsonCreator
    public static InformationTechnologyEnum code(int code) {
        for (InformationTechnologyEnum member : InformationTechnologyEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return InformationTechnologyEnum.UNKNOWN;
    }

    @JsonValue
    public int toValue() {
        for (InformationTechnologyEnum member : InformationTechnologyEnum.values()) {
            if (member.getCode() == this.getCode()) {
                return member.getCode();
            }
        }
        return UNKNOWN.code;
    }
}

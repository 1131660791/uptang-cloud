package com.uptang.cloud.score.common.enums;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:47
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 异议状态 1 异议处理期 2 完成
 */
@Getter
@AllArgsConstructor
public enum ObjectionEnum implements IEnumType {

    /**
     * 异议处理期
     */
    PROCESS(1, "异议处理期"),

    /**
     * 异议处理期
     */
    DONE(2, "完成");

    @EnumValue
    private final int code;

    private final String desc;

    @JSONCreator
    @JsonCreator
    public static ObjectionEnum code(int code) {
        for (ObjectionEnum member : ObjectionEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }

    @JsonValue
    public String toValue() {
        for (ObjectionEnum member : ObjectionEnum.values()) {
            if (member.getCode() == this.getCode()) {
                return member.desc;
            }
        }
        return "";
    }
}

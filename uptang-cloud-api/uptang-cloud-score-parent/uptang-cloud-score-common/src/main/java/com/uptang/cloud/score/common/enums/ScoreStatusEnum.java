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
 * @createtime : 2019-11-12 11:54
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 公示 1 公示中 2 归档 3 已提交
 */
@Getter
@AllArgsConstructor
public enum ScoreStatusEnum implements IEnumType {

    /**
     * 公示中
     */
    SHOW(1, "公示中"),

    /**
     * 归档
     */
    ARCHIVE(2, "归档"),

    /**
     * 已提交
     */
    SUBMIT(3, "已提交");

    @EnumValue
    private final int code;

    private final String desc;

    @JsonCreator
    @JSONCreator
    public static ScoreStatusEnum code(int code) {
        for (ScoreStatusEnum member : ScoreStatusEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }

    @JsonValue
    public int toValue() {
        for (ScoreStatusEnum member : ScoreStatusEnum.values()) {
            if (member.getDesc().equals(this.desc)) {
                return member.getCode();
            }
        }
        return 0;
    }
}

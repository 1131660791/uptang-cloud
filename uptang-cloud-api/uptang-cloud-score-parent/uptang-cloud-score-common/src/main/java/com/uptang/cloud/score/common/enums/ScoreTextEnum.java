package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 11:17
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 文本成绩
 */
@Getter
@AllArgsConstructor
public enum ScoreTextEnum implements IEnumType {

    /**
     * 合格
     */
    QUALIFIED(0, "合格"),

    /**
     * 不合格
     */
    FAILED(1, "不合格"),

    UNKNOWN(9, "未识别");

    @EnumValue
    private final int code;

    private final String desc;

    @JsonCreator
    public static ScoreTextEnum code(int code) {
        for (ScoreTextEnum member : ScoreTextEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return ScoreTextEnum.UNKNOWN;
    }

    @JsonValue
    public String toValue() {
        for (ScoreTextEnum member : ScoreTextEnum.values()) {
            if (member.desc.equals(this.desc)) {
                return member.desc;
            }
        }
        return UNKNOWN.desc;
    }
}

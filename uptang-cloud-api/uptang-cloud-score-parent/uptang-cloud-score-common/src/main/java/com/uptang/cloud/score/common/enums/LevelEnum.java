package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 * <p>
 * 90分以上为优秀，75-89分为良好，60-74分为合格，60分以下为不合格。
 */
@Getter
@AllArgsConstructor
public enum LevelEnum implements IEnumType {


    /**
     * 优秀
     */
    BEST(1, "优"),

    /**
     * 良好
     */
    GOOD(2, "良好"),

    /**
     * 合格
     */
    QUALIFIED(3, "合格"),

    /**
     * 不合格
     */
    FAILED(4, "不合格"),

    UNKNOWN(9, "未识别");


    @EnumValue
    private final int code;

    private final String desc;

    public static LevelEnum text(String text) {
        for (LevelEnum member : LevelEnum.values()) {
            if (member.getDesc().equals(text)) {
                return member;
            }
        }
        return LevelEnum.UNKNOWN;
    }

    @JsonCreator
    public static LevelEnum code(int code) {
        for (LevelEnum member : LevelEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return LevelEnum.UNKNOWN;
    }

    @JsonValue
    public String toValue() {
        for (LevelEnum member : LevelEnum.values()) {
            if (member.getDesc().equals(this.desc)) {
                return member.desc;
            }
        }
        return UNKNOWN.desc;
    }
}

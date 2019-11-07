package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 11:17
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 文本成绩
 */
@Getter
@AllArgsConstructor
public enum ScoreTextEnum implements IEnumType {


    QUALIFIED(0, "合格"),

    FAILED(1, "不合格")


    ;

    @EnumValue
    private final int code;

    private final String desc;

    private final static Map<Integer, ScoreTextEnum> BY_CODE_MAP =
            Arrays.stream(ScoreTextEnum.values())
                    .collect(Collectors.toMap(ScoreTextEnum::getCode, type -> type));

    private final static Map<String, ScoreTextEnum> BY_NAME_MAP
            = Arrays.stream(ScoreTextEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static ScoreTextEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static ScoreTextEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static ScoreTextEnum code(int code) {
        for (ScoreTextEnum member : ScoreTextEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

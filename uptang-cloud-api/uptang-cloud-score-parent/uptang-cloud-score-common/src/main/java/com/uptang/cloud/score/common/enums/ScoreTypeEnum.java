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
 * @createtime : 2019-11-06 10:40
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
 */
@Getter
@AllArgsConstructor
public enum ScoreTypeEnum implements IEnumType {

    ACADEMIC(0, "学业成绩"),

    HEALTH(1, "体质健康成绩"),

    ART(2, "艺术成绩");

    @EnumValue
    private final int code;

    private final String desc;


    private final static Map<Integer, ScoreTypeEnum> BY_CODE_MAP =
            Arrays.stream(ScoreTypeEnum.values())
                    .collect(Collectors.toMap(ScoreTypeEnum::getCode, type -> type));

    private final static Map<String, ScoreTypeEnum> BY_NAME_MAP
            = Arrays.stream(ScoreTypeEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static ScoreTypeEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static ScoreTypeEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static ScoreTypeEnum code(int code) {
        for (ScoreTypeEnum member : ScoreTypeEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

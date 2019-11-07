package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:55
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 学期 0 上半学期 1 下班学期
 */
@Getter
@AllArgsConstructor
public enum SemesterEnum implements IEnumType {

    LAST(0, "上半学期"),

    NEXT(1, "下半学期");

    @EnumValue
    private final int code;

    private final String desc;

    private final static Map<Integer, SemesterEnum> BY_CODE_MAP =
            Arrays.stream(SemesterEnum.values())
                    .collect(Collectors.toMap(SemesterEnum::getCode, type -> type));

    private final static Map<String, SemesterEnum> BY_NAME_MAP
            = Arrays.stream(SemesterEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static SemesterEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static SemesterEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static SemesterEnum code(int code) {
        for (SemesterEnum member : SemesterEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

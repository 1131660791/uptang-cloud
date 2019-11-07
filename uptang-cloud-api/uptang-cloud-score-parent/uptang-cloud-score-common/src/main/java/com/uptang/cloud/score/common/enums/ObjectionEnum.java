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
 * @createtime : 2019-11-06 10:47
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 异议 0 无状态 1 异议处理期 2 DONE
 */
@Getter
@AllArgsConstructor
public enum ObjectionEnum implements IEnumType {

    NONE(0, "无状态"),

    SHOW(1, "异议处理期");

    @EnumValue
    private final int code;

    private final String desc;

    private final static Map<Integer, ObjectionEnum> BY_CODE_MAP =
            Arrays.stream(ObjectionEnum.values())
                    .collect(Collectors.toMap(ObjectionEnum::getCode, type -> type));

    private final static Map<String, ObjectionEnum> BY_NAME_MAP
            = Arrays.stream(ObjectionEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static ObjectionEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static ObjectionEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static ObjectionEnum code(int code) {
        for (ObjectionEnum member : ObjectionEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

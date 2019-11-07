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
 * @createtime : 2019-11-06 11:09
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 信息技术，成绩为等级只有四个等级: 0 A ,1 B ,2 C ,3 D
 */
@Getter
@AllArgsConstructor
public enum InformationTechnologyEnum implements IEnumType {

    A(0),

    B(1),

    C(2),

    D(3);

    @EnumValue
    private final int code;

    @Override
    public String getDesc() {
        return name();
    }

    private final static Map<Integer, InformationTechnologyEnum> BY_CODE_MAP =
            Arrays.stream(InformationTechnologyEnum.values())
                    .collect(Collectors.toMap(InformationTechnologyEnum::getCode, type -> type));

    private final static Map<String, InformationTechnologyEnum> BY_NAME_MAP
            = Arrays.stream(InformationTechnologyEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static InformationTechnologyEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static InformationTechnologyEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static InformationTechnologyEnum code(int code) {
        for (InformationTechnologyEnum member : InformationTechnologyEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

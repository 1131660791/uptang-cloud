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
 * @createtime : 2019-11-06 10:50
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 归档 0 已提交 1 已归档 2 撤销归档
 */
@Getter
@AllArgsConstructor
public enum ArchiveEnum implements IEnumType {

    SUBMIT(0, "已提交"),

    ARCHIVED(1, "已归档"),

    CANCEL(2, "撤销归档");

    @EnumValue
    private final int code;

    private final String desc;

    private final static Map<Integer, ArchiveEnum> BY_CODE_MAP =
            Arrays.stream(ArchiveEnum.values())
                    .collect(Collectors.toMap(ArchiveEnum::getCode, type -> type));

    private final static Map<String, ArchiveEnum> BY_NAME_MAP
            = Arrays.stream(ArchiveEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static ArchiveEnum parse(Integer code) {
        return BY_CODE_MAP.getOrDefault(code, ArchiveEnum.SUBMIT);
    }

    public static ArchiveEnum parse(String name) {
        return BY_NAME_MAP.getOrDefault(StringUtils.trimToEmpty(name).toLowerCase(), ArchiveEnum.SUBMIT);
    }

    public static ArchiveEnum code(int code) {
        for (ArchiveEnum member : ArchiveEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

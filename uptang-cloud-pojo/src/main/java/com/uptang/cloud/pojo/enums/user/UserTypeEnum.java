package com.uptang.cloud.pojo.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /**
     * 学生用户
     */
    STUDENT(1, "学生用户"),

    /**
     * 家长用户
     */
    PARENT(2, "家长用户"),

    /**
     * 教师用户
     */
    TEACHER(3, "教师用户"),

    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(9, "系统管理员"),
    ;


    private final static Map<Integer, UserTypeEnum> BY_CODE_MAP =
            Arrays.stream(UserTypeEnum.values())
                    .collect(Collectors.toMap(UserTypeEnum::getCode, type -> type));

    private final static Map<String, UserTypeEnum> BY_NAME_MAP
            = Arrays.stream(UserTypeEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    @EnumValue
    private final int code;
    private final String desc;


    /**
     * 将代码转成枚举
     *
     * @param code 代码
     * @return 转换出来的用户类型
     */
    public static UserTypeEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    /**
     * 将名字转换成枚举
     *
     * @param name 名字
     * @return 转换出来的用户类型
     */
    public static UserTypeEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }
}
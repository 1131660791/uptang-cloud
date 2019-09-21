package com.uptang.cloud.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据类型状态：
 * COM_XXX - 公共(0-4:无效，5-9:有效)
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Getter
@AllArgsConstructor
public enum StateEnum {

    /**
     * 无效，理论上可以从数据库中物理删除
     */
    COM_INACTIVE(0, "无效"),

    /**
     * 被禁用，可以被清理程序逐步解除引用后变成 COM_INACTIVE
     */
    COM_DISABLED(1, "禁用"),

    /**
     * 正在初始化
     */
    COM_INITIALIZE(2, "正在初始化"),

    /**
     * 草稿
     */
    COM_DRAFT(3, "草稿"),


    /**
     * 数据正常有效
     */
    COM_ACTIVE(5, "有效"),

    ;


    private final static Map<Integer, StateEnum> BY_CODE_MAP =
            Arrays.stream(StateEnum.values()).collect(Collectors.toMap(StateEnum::getCode, type -> type));

    private final static Map<String, StateEnum> BY_NAME_MAP
            = Arrays.stream(StateEnum.values()).collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    @EnumValue
    private final int code;
    private final String desc;


    /**
     * 获取枚举对象
     *
     * @param code 代码
     * @return 根据编码转换出来的枚举对象
     */
    public static StateEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    /**
     * 获取枚举对象
     *
     * @param name 名字
     * @return 根据枚举名转换出来的枚举对象
     */
    public static StateEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }
}

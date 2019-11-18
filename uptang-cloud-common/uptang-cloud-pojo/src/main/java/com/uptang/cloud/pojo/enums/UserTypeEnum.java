package com.uptang.cloud.pojo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {
    /**
     * 管理员
     */
    MANAGER(9, "管理员"),

    /**
     * 教师用户
     */
    TEACHER(1, "教师用户"),

    /**
     * 学生用户
     */
    STUDENT(2, "学生用户"),

    /**
     * 家长用户
     */
    PARENT(3, "家长用户"),

    /**
     * 系统管理员
     */
    SYSTEM_ADMIN(9, "系统管理员");


    /**
     * org.springframework.web.util.NestedServletException:
     * Handler dispatch failed; nested exception is java.lang.NoClassDefFoundError: Could not initialize class com.uptang.cloud.pojo.enums.UserTypeEnum
     * at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1054)
     * at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)
     * at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)
     * at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:908)
     * <p>
     * private final static Map<Integer, UserTypeEnum> BY_CODE_MAP =
     * Arrays.stream(UserTypeEnum.values())
     * .collect(Collectors.toMap(UserTypeEnum::getCode, type -> type));
     * <p>
     * private final static Map<String, UserTypeEnum> BY_NAME_MAP
     * = Arrays.stream(UserTypeEnum.values())
     * .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));
     */

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
        UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum typeEnum : values) {
            if (code.compareTo(typeEnum.code) == 0) {
                return typeEnum;
            }
        }
        return null;
    }


    /**
     * 将名字转换成枚举
     *
     * @param name 名字
     * @return 转换出来的用户类型
     */
    public static UserTypeEnum parse(String name) {
        UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum typeEnum : values) {
            if (name.compareTo(typeEnum.desc) == 0) {
                return typeEnum;
            }
        }
        return null;
    }
}
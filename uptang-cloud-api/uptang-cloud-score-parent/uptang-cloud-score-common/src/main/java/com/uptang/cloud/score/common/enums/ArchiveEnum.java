package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:50
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 归档 0 已提交 1 已归档 2 撤销归档
 */
@Getter
@AllArgsConstructor
public enum ArchiveEnum implements IEnumType {

    /**
     * 已提交
     */
    SUBMIT(0, "已提交"),

    /**
     * 已归档
     */
    ARCHIVED(1, "已归档"),

    /**
     * 撤销归档
     */
    CANCEL(2, "撤销归档");

    @EnumValue
    private final int code;

    private final String desc;

    public static ArchiveEnum code(int code) {
        for (ArchiveEnum member : ArchiveEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

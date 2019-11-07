package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 10:45
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 公示 0 无状态 1 公示期 2 撤销公示 3 DONE
 */
@Getter
@AllArgsConstructor
public enum ShowStatEnum implements IEnumType {

    /**
     * 无状态
     */
    NONE(0, "无状态") {
        @Override
        public boolean isShow() {
            return false;
        }
    },

    /**
     * 公示期
     */
    SHOW(1, "公示期") {
        @Override
        public boolean isShow() {
            return true;
        }
    },

    /**
     * 撤销公示
     */
    CANCEL(2, "撤销公示") {
        @Override
        public boolean isShow() {
            return false;
        }
    };

    @EnumValue
    private final int code;

    private final String desc;

    public abstract boolean isShow();

    public static ShowStatEnum code(int code) {
        for (ShowStatEnum member : ShowStatEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

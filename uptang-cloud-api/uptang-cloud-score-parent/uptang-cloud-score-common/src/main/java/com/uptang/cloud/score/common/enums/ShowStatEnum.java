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
 * @createtime : 2019-11-06 10:45
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 公示 0 无状态 1 公示期 2 撤销公示 3 DONE
 */
@Getter
@AllArgsConstructor
public enum ShowStatEnum implements IEnumType {

    NONE(0, "无状态") {
        @Override
        public boolean isShow() {
            return false;
        }
    },

    SHOW(1, "公示期") {
        @Override
        public boolean isShow() {
            return true;
        }
    },

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

    private final static Map<Integer, ShowStatEnum> BY_CODE_MAP =
            Arrays.stream(ShowStatEnum.values())
                    .collect(Collectors.toMap(ShowStatEnum::getCode, type -> type));

    private final static Map<String, ShowStatEnum> BY_NAME_MAP
            = Arrays.stream(ShowStatEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    public static ShowStatEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    public static ShowStatEnum parse(String name) {
        return BY_NAME_MAP.get(StringUtils.trimToEmpty(name).toLowerCase());
    }

    public static ShowStatEnum code(int code) {
        for (ShowStatEnum member : ShowStatEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

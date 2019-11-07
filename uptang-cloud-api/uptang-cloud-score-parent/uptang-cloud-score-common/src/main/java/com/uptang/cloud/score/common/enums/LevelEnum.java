package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 * <p>
 * 90分以上为优秀，75-89分为良好，60-74分为合格，60分以下为不合格。
 */
@Getter
@AllArgsConstructor
public enum LevelEnum {


    BEST(1),

    GOOD(2),

    QUALIFIED(3),

    FAILED(4);


    @EnumValue
    private final int code;

    public static LevelEnum code(int code) {
        for (LevelEnum member : LevelEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

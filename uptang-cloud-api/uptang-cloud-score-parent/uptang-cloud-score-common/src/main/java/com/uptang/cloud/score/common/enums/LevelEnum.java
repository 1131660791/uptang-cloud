package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Getter
@AllArgsConstructor
public enum LevelEnum implements IEnumType {
    /**
     * 优
     */
    BEST(1, "优");

    @EnumValue
    private final int code;
    private final String desc;
}

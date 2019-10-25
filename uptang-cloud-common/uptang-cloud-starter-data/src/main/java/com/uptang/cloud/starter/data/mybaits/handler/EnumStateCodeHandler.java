package com.uptang.cloud.starter.data.mybaits.handler;

import com.uptang.cloud.pojo.enums.StateEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public class EnumStateCodeHandler extends BaseObjectHandler<StateEnum> {
    @Override
    public int getCode(StateEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public StateEnum getObject(int code) {
        return StateEnum.parse(code);
    }
}

package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ObjectionEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:21
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ObjectionEnumTypeHandler extends BaseObjectHandler<ObjectionEnum> {

    @Override
    public int getCode(ObjectionEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ObjectionEnum getObject(int code) {
        return ObjectionEnum.code(code);
    }
}

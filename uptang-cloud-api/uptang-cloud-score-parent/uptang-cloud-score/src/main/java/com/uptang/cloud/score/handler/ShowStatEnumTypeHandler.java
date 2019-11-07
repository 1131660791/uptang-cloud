package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ShowStatEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:19
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ShowStatEnumTypeHandler extends BaseObjectHandler<ShowStatEnum> {

    @Override
    public int getCode(ShowStatEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ShowStatEnum getObject(int code) {
        return ShowStatEnum.code(code);
    }
}

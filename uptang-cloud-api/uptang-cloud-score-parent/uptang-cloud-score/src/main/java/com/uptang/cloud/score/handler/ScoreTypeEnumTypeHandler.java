package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ScoreTypeEnumTypeHandler extends BaseObjectHandler<ScoreTypeEnum> {


    @Override
    public int getCode(ScoreTypeEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ScoreTypeEnum getObject(int code) {
        return ScoreTypeEnum.code(code);
    }
}

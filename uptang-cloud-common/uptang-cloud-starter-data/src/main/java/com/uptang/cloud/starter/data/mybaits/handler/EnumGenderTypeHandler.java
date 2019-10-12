package com.uptang.cloud.starter.data.mybaits.handler;

import com.uptang.cloud.pojo.enums.GenderEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public class EnumGenderTypeHandler extends BaseObjectHandler<GenderEnum> {
    @Override
    public int getCode(GenderEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public GenderEnum getObject(int code) {
        return GenderEnum.parse(code);
    }
}

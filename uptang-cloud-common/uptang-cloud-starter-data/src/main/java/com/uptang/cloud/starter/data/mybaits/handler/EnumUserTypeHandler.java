package com.uptang.cloud.starter.data.mybaits.handler;

import com.uptang.cloud.pojo.enums.UserTypeEnum;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public class EnumUserTypeHandler extends BaseObjectHandler<UserTypeEnum> {
    @Override
    public int getCode(UserTypeEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public UserTypeEnum getObject(int code) {
        return UserTypeEnum.parse(code);
    }
}

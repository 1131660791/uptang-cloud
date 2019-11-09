package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-09 19:57
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@MappedTypes(Integer.class)
@MappedJdbcTypes({JdbcType.TINYINT})
public class SemesterEnumTypeHandler extends BaseObjectHandler<SemesterEnum> {

    @Override
    public int getCode(SemesterEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public SemesterEnum getObject(int code) {
        return SemesterEnum.code(code);
    }
}


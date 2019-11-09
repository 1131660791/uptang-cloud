package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.SubjectEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-09 19:59
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@MappedTypes(Integer.class)
@MappedJdbcTypes({JdbcType.TINYINT})
public class SubjectEnumTypeHandler extends BaseObjectHandler<SubjectEnum> {

    @Override
    public int getCode(SubjectEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public SubjectEnum getObject(int code) {
        return SubjectEnum.code(code);
    }
}
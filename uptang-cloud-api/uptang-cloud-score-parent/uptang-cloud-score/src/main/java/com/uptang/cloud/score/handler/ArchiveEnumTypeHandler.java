package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ArchiveEnum;
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
public class ArchiveEnumTypeHandler extends BaseObjectHandler<ArchiveEnum> {

    @Override
    public int getCode(ArchiveEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ArchiveEnum getObject(int code) {
        return ArchiveEnum.code(code);
    }
}
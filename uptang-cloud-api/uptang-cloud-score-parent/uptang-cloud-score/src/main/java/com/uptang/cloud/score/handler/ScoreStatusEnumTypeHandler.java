package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ScoreStatusEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-17 1:00
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@MappedTypes(ScoreStatusEnum.class)
@MappedJdbcTypes({JdbcType.TINYINT})
public class ScoreStatusEnumTypeHandler extends BaseObjectHandler<ScoreStatusEnum> {

    @Override
    public int getCode(ScoreStatusEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ScoreStatusEnum getObject(int code) {
        return ScoreStatusEnum.code(code);
    }
}

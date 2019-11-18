package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ReviewEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-17 1:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@MappedTypes(ReviewEnum.class)
@MappedJdbcTypes({JdbcType.TINYINT})
public class ReviewEnumTypeHandler extends BaseObjectHandler<ReviewEnum> {
    @Override
    public int getCode(ReviewEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public ReviewEnum getObject(int code) {
        return ReviewEnum.code(code);
    }
}

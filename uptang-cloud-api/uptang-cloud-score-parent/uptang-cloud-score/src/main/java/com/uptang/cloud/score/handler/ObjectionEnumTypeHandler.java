package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ObjectionEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:21
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ObjectionEnumTypeHandler extends BaseTypeHandler<ObjectionEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ObjectionEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public ObjectionEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ObjectionEnum.code(rs.getInt(columnName));
    }

    @Override
    public ObjectionEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ObjectionEnum.code(rs.getInt(columnIndex));
    }

    @Override
    public ObjectionEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ObjectionEnum.code(cs.getInt(columnIndex));
    }
}

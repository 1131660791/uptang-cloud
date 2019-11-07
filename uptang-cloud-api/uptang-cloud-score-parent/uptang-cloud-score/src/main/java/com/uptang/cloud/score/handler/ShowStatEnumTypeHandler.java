package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ShowStatEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:19
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ShowStatEnumTypeHandler extends BaseTypeHandler<ShowStatEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ShowStatEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public ShowStatEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ShowStatEnum.code(rs.getInt(columnName));
    }

    @Override
    public ShowStatEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ShowStatEnum.code(rs.getInt(columnIndex));
    }

    @Override
    public ShowStatEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ShowStatEnum.code(cs.getInt(columnIndex));
    }
}

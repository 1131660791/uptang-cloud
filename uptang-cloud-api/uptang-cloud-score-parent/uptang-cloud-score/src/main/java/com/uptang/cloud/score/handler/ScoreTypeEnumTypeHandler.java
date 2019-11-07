package com.uptang.cloud.score.handler;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 17:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ScoreTypeEnumTypeHandler extends BaseTypeHandler<ScoreTypeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ScoreTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, parameter.getCode());
    }

    @Override
    public ScoreTypeEnum getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return ScoreTypeEnum.code(resultSet.getInt(columnName));
    }

    @Override
    public ScoreTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ScoreTypeEnum.code(resultSet.getInt(i));
    }

    @Override
    public ScoreTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ScoreTypeEnum.code(callableStatement.getInt(i));
    }
}

package com.uptang.cloud.starter.data.mybaits.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public abstract class BaseObjectHandler<T> extends BaseTypeHandler<T> {

    /**
     * 通过对象获得数字(存储在DB中)
     *
     * @param enumeration 枚举数据
     * @return DB中存储的数字
     */
    public abstract int getCode(T enumeration);

    /**
     * 通过数字(存储在DB中)获取得对象
     *
     * @param code 代码
     * @return 数字对应的枚举
     */
    public abstract T getObject(int code);


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, getCode(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getObject(rs.getInt(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getObject(rs.getInt(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getObject(cs.getInt(columnIndex));
    }
}

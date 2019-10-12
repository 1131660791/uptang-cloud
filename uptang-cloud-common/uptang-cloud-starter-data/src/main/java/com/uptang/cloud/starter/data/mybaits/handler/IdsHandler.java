package com.uptang.cloud.starter.data.mybaits.handler;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public class IdsHandler extends BaseTypeHandler<Long[]> {
    private static final char SEPARATOR = ',';

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ArrayUtils.isEmpty(parameter) ? "" : StringUtils.join(parameter, SEPARATOR));
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? new Long[0] : getIds(rs.getString(columnName));
    }

    @Override
    public Long[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : getIds(rs.getString(columnIndex));
    }

    @Override
    public Long[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : getIds(cs.getString(columnIndex));
    }

    /**
     * 将字符串隔开的 Ids 转成数字数组.
     *
     * @param strIds 用 ',' 分隔的数字
     * @return Integer[]
     */
    private Long[] getIds(String strIds) {
        if (StringUtils.isBlank(strIds)) {
            return new Long[0];
        }

        return Arrays.stream(StringUtils.split(strIds, SEPARATOR))
                .filter(StringUtils::isNotBlank)
                .map(id -> NumberUtils.toLong(id, -101))
                .filter(id -> id > -100)
                .toArray(Long[]::new);
    }
}
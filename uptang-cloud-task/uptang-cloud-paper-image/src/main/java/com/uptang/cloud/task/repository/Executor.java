package com.uptang.cloud.task.repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@FunctionalInterface
public interface Executor<T> {
    /**
     * 执行SQL
     *
     * @param connection 数据库连接
     * @return 查询到的数据
     * @throws SQLException 执行异常
     */
    T execute(Connection connection) throws SQLException;
}

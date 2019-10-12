package com.uptang.cloud.task.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Slf4j
public class ConnectionManager {
    private final ConcurrentMap<String, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();

    private ConnectionManager() {
    }

    /**
     * 运用JVM类加载的特点，在主动引用时初始化
     */
    private static class Holder {
        private static ConnectionManager instance = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return Holder.instance;
    }

    public Connection getConnection(String examCode) throws SQLException {
        DataSource dataSource = DATA_SOURCE_MAP.get(examCode);
        if (Objects.isNull(dataSource)) {
            dataSource = createDataSource(examCode);
            DATA_SOURCE_MAP.putIfAbsent(examCode, dataSource);
        }
        return dataSource.getConnection();
    }

    private DataSource createDataSource(String examCode) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://192.168.0.210:3306/" + examCode);
        config.setUsername("uptang");
        config.setPassword("uptang408*");
        config.setMaximumPoolSize(5);

        config.addDataSourceProperty("dataSource.databaseName", examCode);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}

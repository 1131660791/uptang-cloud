package com.uptang.cloud.task.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Component
public class ConnectionManager {
    private static final ConcurrentMap<String, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();

    /**
     * 获取数据库配置信息
     */
    @Value("${spring.datasource.host:192.168.0.210}")
    private String dbHost;

    @Value("${spring.datasource.port:3306}")
    private String dbPort;

    @Value("${spring.datasource.username:uptang}")
    private String dbUsername;

    @Value("${spring.datasource.password:uptang408*}")
    private String dbPassword;

    /**
     * 获取数据库连接
     *
     * @param examCode 考试项目代码
     * @return 数据库连接
     * @throws SQLException
     */
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
        config.setMaximumPoolSize(5);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        String jdbcUrlTpl = "jdbc:mysql://%s:%s/%s?useUnicode=true&useSSL=false&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai";
        config.setJdbcUrl(String.format(jdbcUrlTpl, dbHost, dbPort, examCode));
        config.setUsername(dbUsername);
        config.setPassword(dbPassword);

        return new HikariDataSource(config);
    }
}

package com.uptang.cloud.task.repository;

import com.google.common.collect.Lists;
import com.uptang.cloud.core.exception.DataAccessException;
import com.uptang.cloud.task.mode.PaperScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Slf4j
@Repository
public class PaperRepository {
    private String examCode = "xty_20190617150608344";
    private static final String SQL = "SELECT `id`, `zkzh`, `kmdm`, 'image_path' AS path FROM `xty_scan` WHERE `id` > %s LIMIT %s";

    /**
     * 获取扫描的答题卡
     *
     * @param examCode 考试代码
     * @param prevId   最后一次查询的ID
     * @param count    查询数量
     * @return 答题卡
     */
    public List<PaperScan> getPapers(String examCode, int prevId, int count) {
        Statement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(examCode)) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(SQL, prevId, count));

            List<PaperScan> papers = Lists.newArrayListWithCapacity(count);
            while (resultSet.next()) {
                papers.add(PaperScan.builder()
                        .id(resultSet.getInt("id"))
                        .ticketNumber(resultSet.getString("zkzh"))
                        .subjectCode(resultSet.getString("kmdm"))
                        .imagePath(resultSet.getString("path"))
                        .build());
            }
            return papers;
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } finally {
            try {
                if (Objects.nonNull(resultSet)) {
                    resultSet.close();
                }
                if (Objects.nonNull(statement)) {
                    statement.close();
                }
            } catch (Exception ex) {
                // Noting
            }
        }
    }
}

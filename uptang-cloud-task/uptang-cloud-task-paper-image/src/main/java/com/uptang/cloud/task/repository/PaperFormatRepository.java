package com.uptang.cloud.task.repository;

import com.uptang.cloud.core.exception.DataAccessException;
import com.uptang.cloud.task.mode.PaperFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Slf4j
@Repository
public class PaperFormatRepository {
    private static final String SQL = "SELECT `id`, `format_id`, `item_num`, `area` FROM `xty_paper_cat` WHERE`item_type` = 2 ORDER BY `id`";

    private final ConnectionManager connectionManager;

    @Autowired
    public PaperFormatRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    /**
     * 得到所有试卷格式
     *
     * @param examCode 考试代码
     * @return 试卷格式
     */
    public List<PaperFormat> getAllFormats(String examCode) {
        Statement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionManager.getConnection(examCode)) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);

            List<PaperFormat> formats = new ArrayList<>();
            while (resultSet.next()) {
                formats.add(PaperFormat.builder()
                        .id(resultSet.getInt("id"))
                        .itemNum(resultSet.getString("item_num"))
                        .formatId(resultSet.getInt("format_id"))
                        .formatContent(resultSet.getString("area"))
                        .build());
            }
            return formats;
        } catch (Exception ex) {
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

package com.uptang.cloud.task.repository;

import com.google.common.collect.Lists;
import com.uptang.cloud.core.exception.DataAccessException;
import com.uptang.cloud.task.mode.PaperScan;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
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
    private static final String QUERY_SQL = "SELECT `id`, `zkzh`, `kmdm`, `format_id`, `path` FROM `xty_scan` WHERE `id` > ? AND path IS NOT NULL AND `crop_state` = 0 LIMIT ?";

    /**
     * 获取扫描的答题卡
     *
     * @param examCode 考试代码
     * @param prevId   最后一次查询的ID
     * @param count    查询数量
     * @return 答题卡
     */
    public List<PaperScan> getPapers(String examCode, int prevId, int count) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(examCode)) {
            statement = connection.prepareStatement(QUERY_SQL);
            statement.setInt(1, prevId);
            statement.setInt(2, count);
            resultSet = statement.executeQuery();

            List<PaperScan> papers = Lists.newArrayListWithCapacity(count);
            while (resultSet.next()) {
                papers.add(PaperScan.builder()
                        .id(resultSet.getInt("id"))
                        .ticketNumber(resultSet.getString("zkzh"))
                        .subjectCode(resultSet.getString("kmdm"))
                        .formatId(resultSet.getInt("format_id"))
                        .imagePath(resultSet.getString("path"))
                        .build());
            }
            return papers;
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


    /**
     * 更新答题卡裁剪状态
     *
     * @param examCode 考试代码
     * @param state    更新后的状态
     * @param ids      需要更新的试卷ID
     * @return 更新成功的行数
     */
    public Integer updatePaperCropState(String examCode, Integer state, Collection<Integer> ids) {
        StringBuilder sqlBuilder = new StringBuilder(100);
        sqlBuilder.append("UPDATE `xty_scan` SET `crop_state` = ").append(state);
        sqlBuilder.append(" WHERE `id` IN (").append(StringUtils.join(ids, ", ")).append(")");

        Statement statement = null;
        try (Connection connection = ConnectionManager.getInstance().getConnection(examCode)) {
            statement = connection.createStatement();
            return statement.executeUpdate(sqlBuilder.toString());
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        } finally {
            try {
                if (Objects.nonNull(statement)) {
                    statement.close();
                }
            } catch (Exception ex) {
                // Noting
            }
        }
    }
}

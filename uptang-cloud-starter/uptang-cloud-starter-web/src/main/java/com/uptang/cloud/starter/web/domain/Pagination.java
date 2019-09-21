package com.uptang.cloud.starter.web.domain;

import com.uptang.cloud.starter.common.util.NumberUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString
@NoArgsConstructor
public class Pagination {
    /**
     * 数据量
     */
    @ApiModelProperty(notes = "数据总行数")
    private Integer totalRow;

    /**
     * 每页最多显示多少条数据
     */
    @ApiModelProperty(notes = "每页显示行数")
    private Integer pageSize;

    /**
     * 当前页码
     */
    @ApiModelProperty(notes = "当前页码")
    private Integer pageIndex;

    /**
     * 总页数
     */
    @ApiModelProperty(notes = "总页数")
    private Integer pageCount;

    @Builder
    public Pagination(Integer totalRow, Integer pageSize, Integer pageIndex) {
        this.totalRow = totalRow;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        if (NumberUtils.isPositive(totalRow) && NumberUtils.isPositive(pageSize)) {
            this.pageCount = (int) (Math.ceil(1.0 * totalRow / pageSize));
        }
    }
}

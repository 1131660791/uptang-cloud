package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-06 10:32:21
 * @mailTo: lmy@uptang.com.cn
 * @summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ArchiveVO extends BaseVO<ArchiveVO> implements Serializable, Cloneable {

    /**
     * @comment Primary key
     */
    @ApiModelProperty(notes = "归档ID")
    private Long id;

    /**
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型编码")
    private Integer type;

    /**
     * @comment 成绩类型
     */
    @ApiModelProperty(notes = "成绩类型")
    private String typeText;

    /**
     * @comment 成绩详情 {语文:85.5,数学:85.5,英语:89.5}
     */
    @ApiModelProperty(notes = "成绩详情")
    private String detail;
}


package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
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
public class ScoreVO extends BaseVO<ScoreVO> implements Serializable, Cloneable {

    /**
     * @comment Primary key
     */
    @ApiModelProperty(notes = "成绩ID")
    private Long id;

    @ApiModelProperty(notes = "成绩类型编码 0 学业成绩 1 体质健康 2 艺术成绩")
    private Integer type;

    /**
     * @comment 成绩类型 0 学业成绩 1 体质健康 2 艺术成绩
     */
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private String typeText;

    /**
     * @comment 科目 数学 语文 英语 物理 地理 化学 (0 表示没有科目)
     */
    @NotNull
    @ApiModelProperty(notes = "科目编码")
    private Integer subject;

    /**
     * @comment 科目 数学 语文 英语 物理 地理 化学 (0 表示没有科目)
     */
    @ApiModelProperty(notes = "科目")
    private String subjectText;

    /**
     * @comment 文本成绩 E.g: 合格、不合格
     */
    @ApiModelProperty(notes = "文本成绩")
    private String scoreText;

    /**
     * @comment 数字成绩 E.g: 85.2 (0 表示没有成绩)
     */
    @ApiModelProperty(notes = "数字成绩")
    private Double scoreNumber;
}


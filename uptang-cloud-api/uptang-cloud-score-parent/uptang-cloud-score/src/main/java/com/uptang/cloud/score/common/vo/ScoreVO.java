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
    @ApiModelProperty(notes = "归档ID")
    private String scoreText;

    /**
     * @comment 数字成绩 E.g: 85.2 (0 表示没有成绩)
     */
    @ApiModelProperty(notes = "归档ID")
    private Integer scoreNumber;
}


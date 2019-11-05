package com.uptang.cloud.score.common.vo;

import com.uptang.cloud.score.common.enums.LevelEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ScoreVO extends BaseVO<ScoreVO> implements Serializable, Cloneable {
    private static final long serialVersionUID = -5714151051326847900L;

    /**
     * 附件ID
     */
    @ApiModelProperty(notes = "附件ID")
    private Long id;

    /**
     * 等级划分
     */
    @ApiModelProperty(notes = "等级划分")
    private LevelEnum level;

    @ApiModelProperty(notes = "等级代码")
    private Integer levelCode;

    @ApiModelProperty(notes = "等级描述")
    private String levelDesc;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 最后修改时间
     */
    private Date updatedTime;
}

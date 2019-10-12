package com.uptang.cloud.base.common.vo;

import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.pojo.enums.StateEnum;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AttachmentVO extends BaseVO<AttachmentVO> implements Serializable, Cloneable {
    private static final long serialVersionUID = 707665344965212353L;

    /**
     * 附件ID
     */
    @ApiModelProperty(notes = "附件ID")
    private Long id;

    /**
     * 扩展名
     */
    @ApiModelProperty(notes = "扩展名")
    private String extName;

    /**
     * 源文件名
     */
    @ApiModelProperty(notes = "源文件名")
    private String srcName;

    /**
     * 附件描述
     */
    @ApiModelProperty(notes = "附件描述")
    private String description;

    /**
     * 外网访问相对路径
     */
    @ApiModelProperty(notes = "相对路径")
    private String relativePath;

    @ApiModelProperty(notes = "访问全路径")
    private String fullPath;

    /**
     * 附件类型
     */
    @ApiModelProperty(notes = "附件类型")
    private AttachmentEnum type;

    @ApiModelProperty(notes = "附件类型代码")
    private Integer typeCode;

    @ApiModelProperty(notes = "附件类型描述")
    private String typeDesc;

    /**
     * 附件大小
     */
    @ApiModelProperty(notes = "附件大小")
    private Long size;

    /**
     * 状态
     */
    @ApiModelProperty(notes = "附件状态")
    private StateEnum state;

    @ApiModelProperty(notes = "附件状态代码")
    private Integer stateCode;

    @ApiModelProperty(notes = "附件状态描述")
    private String stateDesc;
}

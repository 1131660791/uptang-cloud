package com.uptang.cloud.base.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PaperImageSource {
    @ApiModelProperty(notes = "图片ID")
    private Long id;

    @ApiModelProperty(notes = "图片相对路径")
    private String path;

    @NotNull(message = "图片截取X轴")
    @Range(min = 0, max = 4096, message = "图片截取X轴超出范围")
    @ApiModelProperty(notes = "图片截取X轴")
    private Integer x;

    @NotNull(message = "图片截取Y轴")
    @Range(min = 0, max = 4096, message = "图片截取Y轴超出范围")
    @ApiModelProperty(notes = "图片截取Y轴")
    private Integer y;

    @NotNull(message = "图片截取宽度")
    @Range(min = 0, max = 4096, message = "图片截取宽度超出范围")
    @ApiModelProperty(notes = "图片截取宽度")
    private Integer width;

    @NotNull(message = "图片截取高度")
    @Range(min = 0, max = 4096, message = "图片截取高度超出范围")
    @ApiModelProperty(notes = "图片截取高度")
    private Integer height;

    @Builder
    public PaperImageSource(String path, Integer x, Integer y, Integer width, Integer height) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}

package com.uptang.cloud.base.common.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.uptang.cloud.base.common.model.Area;
import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"id", "parentId", "name", "mergedName", "initial", "level", "cityCode", "zipCode"})
public class AreaVO extends BaseVO<AreaVO> {
    @ApiModelProperty(notes = "城市ID")
    private Integer id;

    @ApiModelProperty(notes = "父城市ID")
    private Integer parentId;

    @ApiModelProperty(notes = "城市名称")
    private String name;

    @ApiModelProperty(notes = "合并后的城市全称")
    private String mergedName;

    @ApiModelProperty(notes = "城市名称首字母")
    private String initial;

    @ApiModelProperty(notes = "城市级别")
    private Integer level;

    @ApiModelProperty(notes = "城市代码")
    private String cityCode;

    @ApiModelProperty(notes = "城市邮编")
    private String zipCode;

    public AreaVO(Area base) {
        Optional.ofNullable(base).ifPresent(area -> {
            this.id = area.getId();
            this.parentId = area.getParentId();
            this.name = area.getName();
            this.mergedName = area.getMergedName();
            this.initial = area.getInitial();
            this.level = area.getLevel();
            this.cityCode = area.getCityCode();
            this.zipCode = area.getZipCode();
        });
    }
}
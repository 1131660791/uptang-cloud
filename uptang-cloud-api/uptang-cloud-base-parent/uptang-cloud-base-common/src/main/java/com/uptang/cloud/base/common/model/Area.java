package com.uptang.cloud.base.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("base_area")
public class Area extends BaseModel {
    private static final long serialVersionUID = 6545751452680215897L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private String name;
    private String mergedName;
    private String initial;
    private Integer level;
    private String cityCode;
    private String zipCode;

    @Override
    public Area clone() {
        try {
            return (Area) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

package com.uptang.cloud.user.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息 领域模型
 * @author cht
 * @date 2019-11-19
 */
@ApiModel(value = "用户信息 领域模型")
@Data
@TableName(value = "user")
public class User {
    /**
     * 编号
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 姓名
     */
    @TableField(value = "XM")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 年龄
     */
    @TableField(value = "NL")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 性别 1男 2女
     */
    @TableField(value = "XB")
    @ApiModelProperty(value = "性别 1男 2女")
    private GenderEnum gender;

}
package com.uptang.cloud.user.common.vo;

import com.uptang.cloud.starter.web.domain.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户信息 视图对象
 * @author cht
 * @date 2019-11-19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserVO extends BaseVO<UserVO> implements Serializable, Cloneable {
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 性别 1男 2女
     */
    @ApiModelProperty(value = "性别 1男 2女")
    private Integer gender;

    @ApiModelProperty(notes = "性别描述")
    private String genderDesc;
}

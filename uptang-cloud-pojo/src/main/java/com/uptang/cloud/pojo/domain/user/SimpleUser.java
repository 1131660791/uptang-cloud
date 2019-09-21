package com.uptang.cloud.pojo.domain.user;

import com.uptang.cloud.pojo.enums.user.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString
@NoArgsConstructor
public class SimpleUser implements Cloneable, Serializable {
    private static final long serialVersionUID = 1472298051309908863L;

    @ApiModelProperty(notes = "用户ID")
    private Integer userId;

    @ApiModelProperty(notes = "用户名称")
    private String userName;

    @ApiModelProperty(notes = "用户类型")
    private UserTypeEnum userType;

}

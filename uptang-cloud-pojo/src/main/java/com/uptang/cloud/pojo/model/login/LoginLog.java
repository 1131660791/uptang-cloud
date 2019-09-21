package com.uptang.cloud.pojo.model.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.uptang.cloud.pojo.enums.user.UserTypeEnum;
import com.uptang.cloud.pojo.model.BaseModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("usr_login_log")
public class LoginLog extends BaseModel {
    private static final long serialVersionUID = 8571685036551871821L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 授权令牌
     */
    private String token;

    /**
     * 令牌过期时间
     */
    private Date expirationTime;

    /**
     * 登录人主机
     */
    private String requestHost;

    /**
     * 浏览器类型
     */
    private String userAgent;

}
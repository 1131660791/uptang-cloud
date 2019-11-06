package com.uptang.cloud.pojo.domain.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Data
@ToString
@NoArgsConstructor
public class UserContext {
    /**
     * 使用服务的主机
     */
    private String host;

    /**
     * 登录时的授权令牌
     */
    private String token;

    /**
     * 登录成功的用户ID
     */
    private Long userId;

    /**
     * 用户的真名 | 昵称 | 手机号 | 登录名
     */
    private String userName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 调试模式
     */
    private boolean debug;

    @Builder
    public UserContext(String host, String token, Long userId, String userName, Integer userType, String mobile) {
        this.host = host;
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.mobile = mobile;
    }
}

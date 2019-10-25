package com.uptang.cloud.pojo.domain.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chuan.jiang <jiang.chuan@yanwei365.com>
 * @version 2.0.0
 * @date 2018-11-27
 */
@Data
@ToString
@NoArgsConstructor
public class AccessToken implements Serializable, Cloneable {
    private static final long serialVersionUID = 327779900392534563L;

    @ApiModelProperty(notes = "授权令牌")
    private String token;

    @ApiModelProperty(notes = "授权令牌过期时间")
    private Date expiration;

    @ApiModelProperty(notes = "登录的主机")
    private String host;

    @Builder
    public AccessToken(String token, Date expiration, String host) {
        this.token = token;
        this.expiration = expiration;
        this.host = host;
    }
}

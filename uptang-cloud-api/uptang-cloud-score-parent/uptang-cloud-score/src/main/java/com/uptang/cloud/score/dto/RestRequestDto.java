package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:24
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RestRequestDto implements Serializable {

    /**
     * 用户登录后Token信息
     */
    @JsonIgnore
    private String token;

    /**
     * 从第几条数据开始查询
     */
    private Integer offset;

    /**
     * 查询行数
     */
    private Integer rows;

    /**
     * 请求方法
     */
    @JsonIgnore
    private HttpMethod method;
}

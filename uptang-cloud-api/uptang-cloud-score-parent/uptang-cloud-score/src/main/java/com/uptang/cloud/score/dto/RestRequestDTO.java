package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestRequestDTO implements Serializable {

    /**
     * 用户登录后Token信息
     */
    @JsonIgnore
    private String token;

    /**
     * 从第几条数据开始查询
     */
    private Long offset;

    /**
     * 查询行数
     */
    private Long rows;
}

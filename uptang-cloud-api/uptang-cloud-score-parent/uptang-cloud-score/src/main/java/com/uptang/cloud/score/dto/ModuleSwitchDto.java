package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 11:22
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleSwitchDto extends RestRequestDto {

    /**
     * 年级编号
     */
    private Long gradeId;
}
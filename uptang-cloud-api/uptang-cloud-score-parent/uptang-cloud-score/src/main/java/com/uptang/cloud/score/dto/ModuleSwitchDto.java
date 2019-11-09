package com.uptang.cloud.score.dto;

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
public class ModuleSwitchDto extends RestRequestDto {

    /**
     * 年级编号
     */
    private Long gradeId;
}
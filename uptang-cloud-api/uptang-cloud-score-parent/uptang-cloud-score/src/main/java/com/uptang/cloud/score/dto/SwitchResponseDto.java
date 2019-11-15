package com.uptang.cloud.score.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-14 9:23
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class SwitchResponseDto {

    private Long count;

    private List<ModuleSwitchResponseDto> list;
}

package com.uptang.cloud.score.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 9:35
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Data
public class StuListDTO {

    private Long count;

    private List<StudentDTO> list;
}

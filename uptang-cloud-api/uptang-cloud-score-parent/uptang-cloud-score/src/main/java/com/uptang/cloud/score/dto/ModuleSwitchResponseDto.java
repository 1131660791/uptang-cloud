package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 16:41
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
public class ModuleSwitchResponseDto {

    /**
     * id	int	R	编号
     */
    private Long id;

    /**
     * module_type	int	R	模块类型，值为9代表学业成绩、体质测评
     */
    @JsonProperty("module_type")
    private Boolean moduleType;

    /**
     *   is_switch	string	R	是否开启,true是,false否
     */
    @JsonProperty("is_switch")
    private Boolean switched;

    /**
     * start_time	string	R	开始时间
     */
    @JsonProperty("start_time")
    private Date start;

    /**
     * end_time	string	R	结束时间
     */
    @JsonProperty("end_time")
    private Date end;

    /**
     * grade_id	int	R	年级编号
     */
    @JsonProperty("grade_id")
    private Long gradeId;
}

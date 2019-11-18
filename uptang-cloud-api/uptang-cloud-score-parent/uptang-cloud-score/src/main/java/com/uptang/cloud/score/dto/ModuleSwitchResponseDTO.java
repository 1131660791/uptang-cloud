package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 16:41
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 *
 * <pre>
 *     {
 *     "status": "200",
 *     "message": "OK",
 *     "data": {
 *         "id": 1469,
 *         "module_type": "9",
 *         "start_time": "2019-10-17 00:00:00",
 *         "end_time": "2020-2-08 00:00:00",
 *         "is_switch": true
 *     }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleSwitchResponseDTO {

    /**
     * module_type	int	R	模块类型，值为9代表学业成绩、体质测评
     */
    @JsonProperty("module_type")
    private String moduleType;

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

    @Override
    public String toString() {
        return "{\"moduleType\":\"" + moduleType + "\", \"switched\":\"" + switched + "\", \"start\":" + start + ", \"end\":" + end + "}";
    }
}

package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 11:41
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
public class RequestParameter {

    /**
     * 学校ID
     */
    @ApiModelProperty(notes = "学校ID")
    private Long schoolId;

    /**
     * 年级ID
     */
    @ApiModelProperty(notes = "年级ID")
    private Long gradeId;

    /**
     * 班级学校ID
     */
    @ApiModelProperty(notes = "班级学校ID")
    private Long classId;

    /**
     * 学期ID
     */
    @ApiModelProperty(notes = "学期ID")
    private Long semesterId;

    /**
     * 学期名
     */
    @ApiModelProperty(notes = "学期名")
    private String semesterName;

    /**
     * 成绩类型
     */
    @NotNull
    @ApiModelProperty(notes = "成绩类型 0 学业成绩 1 体质健康 2 艺术成绩")
    private ScoreTypeEnum scoreType;

    @JsonIgnore
    private String token;

    @JsonIgnore
    private Long userId;

    @Override
    public String toString() {
        return "{\"schoolId\":\"" + schoolId + "\", \"gradeId\":\"" + gradeId + "\", \"classId\":\"" + classId + "\", \"semesterId\":\"" + semesterId + "\", \"semesterName\":\"" + semesterName + "\", \"scoreType\":\"" + scoreType + "\", \"token\":\"" + token + "\", \"userId\":\"" + userId + "\"}";
    }
}

package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author Lee.m.yin <lmy@uptong.com.cn>
 * @version 4.0.0
 * @date 2019-11-11
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RequestParameter {

    /**
     * 学校ID
     */
    @ApiModelProperty(notes = "学校ID", required = true)
    private Long schoolId;

    /**
     * 年级ID
     */
    @ApiModelProperty(notes = "年级ID", required = true)
    private Long gradeId;

    /**
     * 班级学校ID
     */
    @ApiModelProperty(notes = "班级学校ID")
    private Long classId;

    /**
     * 学期ID
     */
    @ApiModelProperty(notes = "学期ID", required = true)
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
    @ApiModelProperty(notes = "成绩类型 0:学业成绩 1:体质健康 2:艺术成绩", required = true)
    private ScoreTypeEnum scoreType;

    @JsonIgnore
    private String token;

    @JsonIgnore
    private Long userId;


    @Builder
    public RequestParameter(Long schoolId, Long gradeId, Long classId, Long semesterId, String semesterName,
                            ScoreTypeEnum scoreType, String token, Long userId) {
        this.schoolId = schoolId;
        this.gradeId = gradeId;
        this.classId = classId;
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.scoreType = scoreType;
        this.token = token;
        this.userId = userId;
    }
}

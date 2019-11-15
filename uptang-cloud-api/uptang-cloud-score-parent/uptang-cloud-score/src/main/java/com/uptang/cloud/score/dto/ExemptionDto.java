package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExemptionDto extends RestRequestDto {

    /**
     * int	O	年级编号
     */
    private Long gradeId;

    /**
     * int	O	班级编号
     */
    private Long classId;

    public ExemptionDto(String token, Long gradeId, Long classId) {
        super.setToken(token);
        this.gradeId = gradeId;
        this.classId = classId;
    }
}
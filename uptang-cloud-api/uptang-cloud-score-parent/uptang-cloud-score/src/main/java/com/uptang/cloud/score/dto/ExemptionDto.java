package com.uptang.cloud.score.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ExemptionDto extends RestRequestDto {

    /**
     * int	O	年级编号
     */
    private Integer gradeId;
    /**
     * int	O	班级编号
     */
    private Integer classId;
}
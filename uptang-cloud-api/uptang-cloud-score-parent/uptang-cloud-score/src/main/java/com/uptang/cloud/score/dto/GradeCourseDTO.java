package com.uptang.cloud.score.dto;

import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import lombok.Data;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 15:46
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 年级课程信息
 */
@Data
public class GradeCourseDTO {

    /**
     * 科目编号
     */
    private Integer id;

    /**
     * 学科名称
     */
    private String subjectName;

    /**
     * 数据类型
     */
    private DataTypeEnum dataType;

    /**
     * 年级编号
     */
    private ScoreTypeEnum scoreType;

    /**
     * 二级指标编号
     */
    private Integer index;

    /**
     * 数据校验规则
     */
    private String rule;

    /**
     * Excel序号
     */
    private Integer orderNumber;

    /**
     * 数据类型 0 数字类型 1字符串类型
     */
    public enum DataTypeEnum {
        /**
         * 数字类型
         */
        NUMBER(0),

        /**
         * 1字符串类型
         */
        String(1);

        private int code;

        DataTypeEnum(int code) {
            this.code = code;
        }
    }

    @Override
    public String toString() {
        return ":{\"id\":\"" + id + "\", \"subjectName\":\"" + subjectName + "\", \"dataType\":\"" + dataType + "\", \"scoreType\":\"" + scoreType + "\", \"index\":\"" + index + "\", \"rule\":\"" + rule + "\", \"orderNumber\":\"" + orderNumber + "\"}";
    }
}

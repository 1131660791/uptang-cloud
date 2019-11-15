package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.uptang.cloud.score.common.enums.PublicityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 16:14
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicityDTO {

    /**
     * id	int	R	编号
     */
    private Long id;

    /**
     * mkid	int	R	模块编号 1.日常表现2.综合实践活动3.成就奖励4.学业成绩5.体质健康测评6.标志性卡7.学期评价8.毕业评价
     */
    @JsonProperty("mkid")
    private PublicityTypeEnum moduleCode;

    /**
     *  gssj	int	R	公示时间(天)
     */
    @JsonProperty("gssj")
    private Integer days;

    /**
     * 学生确认。0否1是
     */
    @JsonProperty("xsqr")
    private Confirm confirm;

    /**
     * 是否审核.0否 1是 2学生录入教师审 3教师录入评价小组审
     */
    @JsonProperty("sfsh")
    private Review review;

    /**
     * 数据状态
     */
    @JsonProperty("sjzt")
    private Integer status;

    /**
     * gsfw	int	R	公示范围.0.不公示1.全校可见2.本年级可见3.本班可见
     */
    @JsonProperty("gsfw")
    private Range range;


    @Getter
    @AllArgsConstructor
    public enum Range {

        /**
         * 不公示
         */
        NO(0),

        /**
         * 全校可见
         */
        SCHOOL(1),

        /**
         * 本年级可见
         */
        GRADE(2),

        /**
         * 本班可见
         */
        CLASS(3);

        private final int code;

        @JsonCreator
        public static Range code(int code) {
            for (Range member : Range.values()) {
                if (member.getCode() == code) {
                    return member;
                }
            }
            return null;
        }

        @JsonValue
        public int toValue() {
            for (Range member : Range.values()) {
                if (member.getCode() == this.getCode()) {
                    return member.getCode();
                }
            }
            return 0;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Review {

        NO(0),

        YES(1),

        /**
         * 学生录入教师审
         */
        TEACHER(2),

        /**
         * 教师录入评价小组审
         */
        GROUP(3);

        private final int code;

        @JsonCreator
        public static Review code(int code) {
            for (Review member : Review.values()) {
                if (member.getCode() == code) {
                    return member;
                }
            }
            return null;
        }

        @JsonValue
        public int toValue() {
            for (Review member : Review.values()) {
                if (member.getCode() == this.getCode()) {
                    return member.getCode();
                }
            }
            return 0;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Confirm {
        /**
         * 确认
         */
        YES(1),
        /**
         * 未确认
         */
        NO(0);

        private final int code;

        @JsonCreator
        public static Confirm code(int code) {
            for (Confirm member : Confirm.values()) {
                if (member.getCode() == code) {
                    return member;
                }
            }
            return null;
        }

        @JsonValue
        public int toValue() {
            for (Confirm member : Confirm.values()) {
                if (member.getCode() == this.getCode()) {
                    return member.getCode();
                }
            }
            return 0;
        }
    }
}

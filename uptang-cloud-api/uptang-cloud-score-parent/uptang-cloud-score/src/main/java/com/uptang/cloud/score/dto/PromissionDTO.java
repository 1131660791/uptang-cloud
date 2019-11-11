package com.uptang.cloud.score.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 15:51
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromissionDTO {

    /**
     * user	string	R	用户信息json，需自行解析
     */
    private String user;

    /**
     * 角色
     */
    private Integer auth;

    /**
     * user_type	int	R	用户类型 1学生2老师3家长3管理员
     */
    @JsonProperty("user_type")
    private UserType userType;

    /**
     * highest_level	int	R	数据权限最高等级，1省级 2市州 3区 4学校 5年级 6班级
     */
    @JsonProperty("highest_level")
    private HighestLevel highestLevel;

    @Getter
    @AllArgsConstructor
    public enum UserType {
        /**
         * 1学生
         */
        STUDENT(1),

        /**
         * 2老师
         */
        TEACHER(2),
        /**
         * 3家长
         */
        PARENTS(3),
        /**
         * 3管理员
         */
        MANAGER(4);

        private final int code;

        @JsonCreator
        public static UserType forValue(Integer value) {
            for (UserType member : UserType.values()) {
                if (member.getCode() == value) {
                    return member;
                }
            }
            return null;
        }

        @JsonValue
        public int toValue() {
            for (UserType member : UserType.values()) {
                if (member.getCode() == this.getCode()) {
                    return member.getCode();
                }
            }
            return 0;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum HighestLevel {
        /**
         * 1省级 2市州 3区 4学校 5年级 6班级
         * 1省级
         */
        PROVINCE(1),
        /**
         * 2市州
         */
        CITY(2),
        /**
         * 3区
         */
        AREA(3),
        /**
         * 4学校
         */
        SCHOOL(4),
        /**
         * 5年级
         */
        GRADE(5),
        /**
         * 6班级
         */
        CLASS(6);

        private final int code;

        @JsonCreator
        public static HighestLevel forValue(Integer value) {
            for (HighestLevel member : HighestLevel.values()) {
                if (member.getCode() == value) {
                    return member;
                }
            }
            return null;
        }

        @JsonValue
        public int toValue() {
            for (HighestLevel member : HighestLevel.values()) {
                if (member.getCode() == this.getCode()) {
                    return member.getCode();
                }
            }
            return 0;
        }
    }
}

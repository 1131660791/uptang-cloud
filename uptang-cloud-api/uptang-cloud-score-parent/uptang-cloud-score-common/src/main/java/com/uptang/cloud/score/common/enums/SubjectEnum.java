package com.uptang.cloud.score.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 11:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Getter
@AllArgsConstructor
public enum SubjectEnum implements IEnumType {

    CHINESE(1, "语文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    MATH(2, "语文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    ENGLISH(3, "英文", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),

    PHYSICS(4, "物理", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    CHEMISTRY(5, "化学", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    HISTORY(5, "历史", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),

    GEOGRAPHY(7, "地理", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    BIOLOGICAL(8, "生物", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),
    PHYSICAL_CULTURE(9, "体育", "1位小数，最大分100，最小分0分，未上传成绩的不能做0分处理（可为空）"),

    INFORMATION_TECHNOLOGY(10, "信息技术", "成绩为等级：只有四个等级: 0 A ,1 B ,2 C ,3 D'"),
    MUSIC(11, "音乐", "0 合格 1 不合格"),
    ART(12, "美术", "0 合格 1 不合格"),

    PHYSICAL_EXPERIMENT(13, "物理实验", "0 合格 1 不合格"),
    CHEMISTRY_EXPERIMENT(14, "化学实验", "0 合格 1 不合格"),
    BIOLOGICAL_EXPERIMENT(15, "生物实验", "0 合格 1 不合格"),

    LABOR_TECHNICAL_EDUCATION(16, "劳动与技术教育", "0 合格 1 不合格"),
    LOCAL_CURRICULUM(17, "地方及校本课程", "0 合格 1 不合格");


    @EnumValue
    private final int code;

    private final String desc;

    private final String ruleText;

    public static SubjectEnum code(int code) {
        for (SubjectEnum member : SubjectEnum.values()) {
            if (member.getCode() == code) {
                return member;
            }
        }
        return null;
    }
}

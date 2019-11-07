package com.uptang.cloud.starter.common;

import java.io.Serializable;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-04 8:36
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class HealthInfo implements Serializable {

    /**
     * 年级编号
     */
    private String gradeNo;
    /**
     * 班级编号
     */
    private String classNo;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 学籍号
     */
    private String schoolRollNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学生性别 0 男 1 女
     */
    private String gender;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 家庭住址
     */
    private String homeAddress;

}

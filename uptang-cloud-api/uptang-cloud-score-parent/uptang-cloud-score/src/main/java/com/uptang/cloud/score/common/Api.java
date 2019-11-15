package com.uptang.cloud.score.common;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
public interface Api extends com.uptang.cloud.core.Constants {

    /**
     * 管理
     */
    interface Manager {
        /**
         * 任务是否开放接口
         * api/everyday/page_module_switch
         */
        String MODULE_SWITCH = "%s/switch/score";

        /**
         * 公示时间接口
         * <p>
         * typeId: 模块类型编号,
         * 1.日常表现
         * 2.综合实践活动
         * 3.成就奖励
         * 4.学业成绩
         * 5.体质健康测评
         * 6.标志性卡
         * 7.学期评价
         * 8.毕业评价
         * /api/GrowthRecordBag/publicity/detail/{typeId}
         */
        String PUBLICITY = "%s/api/GrowthRecordBag/publicity/detail/%d";
    }

    /**
     * 基础服务
     */
    interface UserCenter {

        /**
         * 学生信息查询接口
         * api/base/student/list
         */
        String STUDENT_INFO = "%s/student/list";

        /**
         * 免测学生列表
         * %s/api/base/student/exemption
         * http://192.168.0.127:8081/student/exemption
         */
        String EXEMPTION = "http://192.168.0.127:8081/student/exemption";
    }

    /**
     * 权限
     */
    interface Promission {

        /**
         * 当前用户权限校验接口
         * /api/base/baseUser/sessionuser
         */
        String CHECK = "%s/baseUser/sessionuser";
    }

    /**
     * 年级信息
     */
    interface Grade {
        /**
         * 年级课程信息
         * /api/base/subject/detail/{gradeId} 年级ID
         */
        String INFO = "%s/course/detail/%d";
    }

    /**
     * 拼接请求路径
     *
     * @param serverHost
     * @param api
     * @return
     */
    static String getApi(String serverHost, String api, Object param) {
        return String.format(api, serverHost, param);
    }

    /**
     * 拼接请求路径
     *
     * @param serverHost
     * @param api
     * @return
     */
    static String getApi(String serverHost, String api) {
        return String.format(api, serverHost);
    }
}
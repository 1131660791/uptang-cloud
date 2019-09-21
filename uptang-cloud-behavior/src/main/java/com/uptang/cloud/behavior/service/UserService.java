package com.uptang.cloud.behavior.service;

import com.uptang.cloud.pojo.model.user.UserInfo;

import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public interface UserService {

    /**
     * 查询用户详情
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    UserInfo load(String userId);


    /**
     * 查询用户列表 (for test)
     *
     * @param searchTerm 查询关键字
     * @return 用户列表
     */
    List<UserInfo> listUsers(String searchTerm);
}

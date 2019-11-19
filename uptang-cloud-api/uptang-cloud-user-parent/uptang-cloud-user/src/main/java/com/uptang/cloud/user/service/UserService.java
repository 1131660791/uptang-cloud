package com.uptang.cloud.user.service;

import com.github.pagehelper.Page;
import com.uptang.cloud.user.common.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户信息 业务接口层
 * @author cht
 * @date 2019-11-19
 */
public interface UserService extends IService<User> {

    /**
     * 分页查询用户信息
     * @return 用户信息分页查询结果
     */
    Page<User> page(Integer pageNum, Integer pageSize);
}






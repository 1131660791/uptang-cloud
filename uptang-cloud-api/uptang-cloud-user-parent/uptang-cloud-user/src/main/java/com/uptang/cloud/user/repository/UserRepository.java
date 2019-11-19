package com.uptang.cloud.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.uptang.cloud.user.common.model.User;

/**
 * 用户信息 数据层
 * @author cht
 * @date 2019-11-19
 */
public interface UserRepository extends BaseMapper<User> {

    /**
     * 分页查询用户信息
     * @return 用户信息分页查询结果
     */
    Page<User> page();
}
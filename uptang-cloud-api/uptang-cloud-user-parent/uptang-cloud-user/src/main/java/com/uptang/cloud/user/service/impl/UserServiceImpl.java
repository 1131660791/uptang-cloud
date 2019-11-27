package com.uptang.cloud.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.user.common.model.User;
import com.uptang.cloud.user.repository.UserRepository;
import com.uptang.cloud.user.service.UserService;

/**
 * 用户信息 业务实现层
 * @author cht
 * @date 2019-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> page(Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> userRepository.page());
    }
}






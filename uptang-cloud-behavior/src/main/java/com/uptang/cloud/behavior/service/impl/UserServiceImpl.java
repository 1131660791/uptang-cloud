package com.uptang.cloud.behavior.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uptang.cloud.behavior.repository.UserInfoRepository;
import com.uptang.cloud.behavior.service.UserService;
import com.uptang.cloud.pojo.model.user.UserInfo;
import com.uptang.cloud.starter.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoRepository, UserInfo> implements UserService {
    @Override
    public UserInfo load(String userId) {
        return getBaseMapper().selectById(userId);
    }

    @Override
    public List<UserInfo> listUsers(String searchTerm) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();

        // 可以单独指定要查询哪些字段
        queryWrapper.select(UserInfo::getId, UserInfo::getUserName, UserInfo::getSchoolName);

        // 关键字查询
        if (StringUtils.isNotBlank(searchTerm)) {
            queryWrapper.and(query -> query.like(UserInfo::getMobile, searchTerm)
                    .or().like(UserInfo::getUserName, searchTerm)
            );
        }

        // 按时间排序
        queryWrapper.orderByDesc(UserInfo::getUpdatedTime);
        return getBaseMapper().selectList(queryWrapper);
    }
}

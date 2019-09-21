package com.uptang.cloud.behavior.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uptang.cloud.pojo.model.user.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Repository
public interface UserInfoRepository extends BaseMapper<UserInfo> {
}

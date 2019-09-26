package com.uptang.cloud.behavior.service;

import com.uptang.cloud.behavior.BaseJunitTest;
import com.uptang.cloud.pojo.model.user.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-26
 */
public class UserServiceTests extends BaseJunitTest {
    @Autowired
    private UserService userService;

    @Test
    public void testListUsers() {
        String searchTerm = "雅安市";
        List<UserInfo> userInfos = userService.listUsers(searchTerm);

        Assert.assertNotNull("用户列表", userInfos);
        userInfos.forEach(this::areaCheck);
    }

    private void areaCheck(UserInfo userInfo) {
        Assert.assertNotNull("用户ID", userInfo.getId());
        Assert.assertNotNull("用户名称", userInfo.getUserName());
    }
}

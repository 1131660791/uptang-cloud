package com.uptang.cloud.user.controller;

import com.github.pagehelper.Page;
import com.uptang.cloud.core.util.CollectionUtils;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import com.uptang.cloud.user.common.converter.UserConverter;
import com.uptang.cloud.user.common.model.User;
import com.uptang.cloud.user.common.vo.UserVO;
import com.uptang.cloud.user.feign.service.SequenceService;
import com.uptang.cloud.user.service.UserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 * @author cht
 * @date 2019-11-19
 */
@Slf4j
@RestController
@RequestMapping("/v1/user")
@Api(value = "UserController", tags = {"用户管理"})
public class UserController {

    private UserService userService;

    private SequenceService sequenceService;

    @Autowired
    public UserController(UserService userService, SequenceService sequenceService) {
        this.userService = userService;
        this.sequenceService = sequenceService;
    }

    @ApiOperation(value = "用户列表", response = UserVO.class)
    @ApiParam(value = "传入json格式", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "条数", paramType = "path", required = true)
    })
    @GetMapping(path = "/list/{pageNum}/{pageSize}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<List<UserVO>> list(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<User> user = userService.page(pageNum, pageSize);
        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(user, models -> {
            if (CollectionUtils.isEmpty(models)) {
                return Collections.emptyList();
            }
            return models.stream().map(UserConverter.INSTANCE::toVo).collect(Collectors.toList());
        }));
    }

    @GetMapping("/test")
    public ApiOut test() {
        return ApiOut.newSuccessResponse(sequenceService.getSequences(50));
    }

}

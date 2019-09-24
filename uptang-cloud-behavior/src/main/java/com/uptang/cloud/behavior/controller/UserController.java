package com.uptang.cloud.behavior.controller;

import com.github.pagehelper.PageHelper;
import com.uptang.cloud.behavior.pojo.converter.UserInfoConverter;
import com.uptang.cloud.behavior.pojo.vo.UserInfoVO;
import com.uptang.cloud.behavior.service.UserService;
import com.uptang.cloud.behavior.util.CacheKeys;
import com.uptang.cloud.pojo.model.user.UserInfo;
import com.uptang.cloud.starter.common.util.CollectionUtils;
import com.uptang.cloud.starter.common.validation.GroupUpdate;
import com.uptang.cloud.starter.data.redis.RedisUtils;
import com.uptang.cloud.starter.web.annotation.JsonResult;
import com.uptang.cloud.starter.web.annotation.PreventRepeatSubmit;
import com.uptang.cloud.starter.web.controller.BaseController;
import com.uptang.cloud.starter.web.domain.ApiOut;
import com.uptang.cloud.starter.web.util.PageableEntitiesConverter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@RestController
@RequestMapping("/v1/users")
@Api(value = "UserController", tags = {"用户展示"})
@PreventRepeatSubmit(timeout = 60, prefix = "user")
public class UserController extends BaseController {
    private final UserService userService;
    private final RedisUtils redisUtils;

    @Autowired
    public UserController(UserService userService, RedisUtils redisUtils) {
        this.userService = userService;
        this.redisUtils = redisUtils;
    }

    /**
     * 查询所有用户列表
     *
     * @param keyword   查询的关键字
     * @param pageIndex 当前页码
     * @param pageSize  每页显示用户条数
     * @return 用户列表
     */
    @ApiOperation(value = "用户列表", response = UserInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "q", value = "查询关键字"),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", defaultValue = "1", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", defaultValue = "10", example = "10", dataType = "int")
    })
    @PreventRepeatSubmit(2)
    @JsonResult(type = UserInfoVO.class, exclude = {"userCode", "createdTime"}, shortDateFormat = true)
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<List<UserInfoVO>> listAllUsers(
            @RequestParam(name = "q", required = false) String keyword,
            @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        // MyBatis 自动分页
        PageHelper.startPage(pageIndex, pageSize);
        List<UserInfo> userInfos = userService.listUsers(keyword);

        return ApiOut.newSuccessResponse(PageableEntitiesConverter.toVos(userInfos, this::toVos));
    }

    /**
     * <pre>
     * 以下输入会在统一异常处理中拦截到异常信息，
     * => "手机号格式不正确(mobile) | 用户名称不能为空(userName)",
     * {
     *   "userName": "",
     *   "userCode": "123",
     *   "email": "abc@",
     *   "mobile": "123",
     *   "schoolName": "七中"
     * }
     * </pre>
     *
     * @param userId     用户ID
     * @param userInfoVO 用户信息
     * @return ApiOut
     */
    @PreventRepeatSubmit(timeout = 30, prefix = "user")
    @ApiOperation(value = "修改用户", response = Boolean.class)
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<Boolean> updateUserInfo(@PathVariable("userId") String userId,
                                          @RequestBody @Validated(GroupUpdate.class) @ApiParam("修改用户") UserInfoVO userInfoVO) {
        // 用于展示数据验证
        return ApiOut.newSuccessResponse(true);
    }

    @ApiOperation(value = "用户详情", response = UserInfo.class)
    @RateLimiter(name = "userInfo")
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiOut<UserInfoVO> getUserInfo(@PathVariable("userId") String userId) {
        // 测试数据缓存
        String cacheKey = CacheKeys.getUserInfoKey(userId);
        UserInfoVO userInfo = redisUtils.mutexGetAndSetIfNeeded(cacheKey, (int) TimeUnit.MINUTES.toSeconds(5),
                () -> Optional.ofNullable(userService.load(userId)).map(UserInfoConverter.INSTANCE::toVo).orElse(null));

        return ApiOut.newSuccessResponse(userInfo);
    }


    /**
     * 将数据转换成VO输出
     *
     * @param userInfos 用户
     * @return 转换后的 VO
     */
    private List<UserInfoVO> toVos(List<UserInfo> userInfos) {
        if (CollectionUtils.isEmpty(userInfos)) {
            return new ArrayList<>(0);
        }
        return userInfos.stream().map(UserInfoConverter.INSTANCE::toVo).collect(Collectors.toList());
    }
}

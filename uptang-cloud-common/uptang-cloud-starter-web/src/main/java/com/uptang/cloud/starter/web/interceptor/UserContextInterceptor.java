package com.uptang.cloud.starter.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.uptang.cloud.pojo.domain.context.UserContextThreadLocal;
import com.uptang.cloud.pojo.domain.user.SerializableToken;
import com.uptang.cloud.pojo.domain.user.UserContext;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.util.HostUtils;
import com.uptang.cloud.starter.web.util.UserTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class UserContextInterceptor extends HandlerInterceptorAdapter {
    private final static String PACKAGE_SEPARATOR = ".";
    private final static String DEFAULT_USER_INFO_API = "/api/base/baseUser/sessionuser.action";
    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    /**
     * 方法是否需要拦截, Key:请求URL的方法， val: true需要拦截
     */
    private final static Cache<HandlerMethod, Boolean> METHOD_INTERCEPT_CACHE = Caffeine.newBuilder()
            .initialCapacity(50).maximumSize(5_000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    /**
     * 第三方系统用户信息缓存
     */
    private final static Cache<String, UserContext> USER_CONTEXT_CACHE = Caffeine.newBuilder()
            .initialCapacity(50).maximumSize(5_000)
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    private final UserTokenUtils userTokenUtils;
    private final String gateHost;

    public UserContextInterceptor(UserTokenUtils userTokenUtils, String gateHost) {
        this.userTokenUtils = userTokenUtils;
        this.gateHost = gateHost;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 只需要拦截我们自己的接口
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Boolean needIntercept = METHOD_INTERCEPT_CACHE.get(handlerMethod, method -> StringUtils.equals(
                Arrays.stream(StringUtils.split(this.getClass().getPackage().getName(), PACKAGE_SEPARATOR, 4)).limit(3).collect(Collectors.joining(PACKAGE_SEPARATOR)),
                Arrays.stream(StringUtils.split(method.getMethod().getDeclaringClass().getPackage().getName(), PACKAGE_SEPARATOR, 4)).limit(3).collect(Collectors.joining(PACKAGE_SEPARATOR))));

        // 从缓存中获取方法的拦截状态，如果缓存中没有数据，则需要计算是否需要拦截
        if (!Optional.ofNullable(needIntercept).orElse(true)) {
            return true;
        }

        // 授权令牌
        String token = StringUtils.trimToNull(request.getHeader(Constants.TOKEN_PARA_NAME));
        if (StringUtils.isNotBlank(token)) {
            userTokenUtils.offer(token);
            UserContext context = userTokenUtils.getUserContext(token);
            SerializableToken serializableToken;
            if (Objects.isNull(context) && Objects.nonNull(serializableToken = userTokenUtils.getSerializableToken(token))) {
                Integer userType = serializableToken.getUserType();
                context = UserContext.builder()
                        .userId(serializableToken.getUserId())
                        .userType(userType)
                        .build();
            }

            // 解决老系统兼容问题，调用远程接口获取用户信息
            if (Objects.isNull(context) && StringUtils.isNotBlank(gateHost)) {
                context = getUserContextFromOtherSystem(token);
            }

            UserContextThreadLocal.set(Optional.ofNullable(context).orElse(new UserContext()));
            UserContextThreadLocal.get().setToken(StringUtils.trimToEmpty(token));
        }

        // 请求者IP
        UserContextThreadLocal.get().setHost(HostUtils.getRequestIp(request));

        // 检查是否调试模式
        UserContextThreadLocal.get().setDebug(BooleanUtils.toBoolean(StringUtils.trimToNull(request.getParameter(Constants.PARA_DEBUG))));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextThreadLocal.remove();
    }

    /**
     * 解决老系统兼容问题，调用远程接口获取用户信息
     *
     * @param token 授权令牌
     * @return UserContext
     */
    private UserContext getUserContextFromOtherSystem(String token) {
        UserContext context = USER_CONTEXT_CACHE.getIfPresent(token);
        if (Objects.nonNull(context)) {
            log.debug("Got user context form local cache for token: {}", token);
            return context;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Token", token);

        String fullApiUrl = gateHost + DEFAULT_USER_INFO_API;
        String result = REST_TEMPLATE.postForObject(fullApiUrl, new HttpEntity<>(headers), String.class);
        log.debug("Got result[{}] by api: {}", result, fullApiUrl);
        if (StringUtils.isBlank(result)) {
            return null;
        }

        // 根据返回信息获取到用户信息的JSON
        final JSONObject data = Optional.ofNullable(JSON.parseObject(result))
                .map(json -> json.getJSONObject("data"))
                .orElse(null);
        if (Objects.isNull(data)) {
            return null;
        }

        final String userJson = data.getString("user");
        if (StringUtils.isBlank(userJson)) {
            return null;
        }

        context = Optional.ofNullable(JSON.parseObject(userJson)).map(json -> {
            return UserContext.builder()
                    .userId(json.getLong("guid"))
                    .userName(json.getString("name"))
                    .mobile(json.getString("phone"))
                    .userType(data.getInteger("user_type"))
                    .build();
        }).orElse(null);

        // 查到数据后放入缓存
        if (Objects.nonNull(context)) {
            USER_CONTEXT_CACHE.put(token, context);
        }

        return context;
    }
}
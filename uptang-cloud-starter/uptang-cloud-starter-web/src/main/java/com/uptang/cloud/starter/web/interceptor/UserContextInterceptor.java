package com.uptang.cloud.starter.web.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.uptang.cloud.pojo.domain.user.SerializableToken;
import com.uptang.cloud.pojo.domain.user.UserContext;
import com.uptang.cloud.pojo.domain.user.UserContextThreadLocal;
import com.uptang.cloud.starter.common.util.NumberUtils;
import com.uptang.cloud.starter.common.util.StringUtils;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.util.HostUtils;
import com.uptang.cloud.starter.web.util.UserTokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@AllArgsConstructor
public class UserContextInterceptor extends HandlerInterceptorAdapter {
    private final static String PACKAGE_SEPARATOR = ".";

    /**
     * 方法是否需要拦截, Key:请求URL的方法， val: true需要拦截
     */
    private final static Cache<HandlerMethod, Boolean> METHOD_INTERCEPT_CACHE = Caffeine.newBuilder()
            .initialCapacity(50).maximumSize(5_000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    private final UserTokenUtils userTokenUtils;

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
            if (null == context && null != (serializableToken = userTokenUtils.getSerializableToken(token))) {
                Integer userType = serializableToken.getUserType();
                context = UserContext.builder()
                        .userId(serializableToken.getUserId())
                        .userType(NumberUtils.isPositive(userType) ? userType + 100 : userType)
                        .build();
            }
            UserContextThreadLocal.set(Optional.ofNullable(context).orElse(new UserContext()));
            UserContextThreadLocal.get().setToken(StringUtils.trimToEmpty(token));
        }

        // 请求者IP
        UserContextThreadLocal.get().setHost(HostUtils.getRequestIp(request));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextThreadLocal.remove();
    }
}
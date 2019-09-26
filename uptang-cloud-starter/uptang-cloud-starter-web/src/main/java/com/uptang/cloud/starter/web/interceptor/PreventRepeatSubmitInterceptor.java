package com.uptang.cloud.starter.web.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.common.exception.BusinessException;
import com.uptang.cloud.starter.common.util.CacheKeys;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.annotation.PreventRepeatSubmit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-24
 */
@Slf4j
@AllArgsConstructor
public class PreventRepeatSubmitInterceptor extends HandlerInterceptorAdapter {
    /**
     * 用作分布式锁的值
     */
    private static final String LOCK_VALUE_TEMPLATE = "Sign:%s|Token:%s|Api:%s|Method:%s|Length:%s|Query:%s";

    /**
     * 不允许重复提交的间隔时间
     */
    private static final int LOCK_TIME_SECONDS = 5;
    private final StringRedisTemplate redisTemplate;

    /**
     * 方法是否需要拦截, Key:请求URL的方法， val: true需要拦截
     */
    private final static Cache<HandlerMethod, Boolean> METHOD_INTERCEPT_CACHE = Caffeine.newBuilder()
            .initialCapacity(50).maximumSize(5_000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 如果是 Debug, 则忽略重复提交检查
        if (BooleanUtils.toBoolean(request.getParameter(Constants.PARA_DEBUG))) {
            log.info("调试模式，对API({})不需要重复提交检查", request.getRequestURI());
            return true;
        }

        // 拦截的方法等信息
        Method handlerMethod = ((HandlerMethod) handler).getMethod();
        Class handlerClass = handlerMethod.getDeclaringClass();
        String signature = handlerClass.getName() + '#' + handlerMethod.getName();
        PreventRepeatSubmit annotation = Optional.ofNullable(handlerMethod.getAnnotation(PreventRepeatSubmit.class))
                .orElse((PreventRepeatSubmit) handlerClass.getAnnotation(PreventRepeatSubmit.class));

        // 如果没有配置必须检查，则直接放行
        if (Objects.isNull(annotation)) {
            log.info("对({}-{})不需要重复提交检查", signature, request.getRequestURI());
            return true;
        }

        // 锁定时间，以用户输入优先
        int lockTime = Math.max(annotation.value(), annotation.timeout());
        lockTime = lockTime > 0 ? lockTime : LOCK_TIME_SECONDS;

        // 对重复提交的请求加锁
        String lockVal = String.format(LOCK_VALUE_TEMPLATE, signature, request.getHeader(Constants.TOKEN_PARA_NAME),
                request.getRequestURI(), request.getMethod(), request.getContentLengthLong(), request.getQueryString());

        String lockKey = CacheKeys.getPreventRepeatSubmitKey(annotation.prefix(), DigestUtils.sha1Hex(lockVal));
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockVal, lockTime, TimeUnit.SECONDS);
        if (Optional.ofNullable(locked).orElse(false)) {
            return true;
        }

        log.debug("API({})重复提交", request.getRequestURI());
        throw new BusinessException(ResponseCodeEnum.REPEAT_SUBMIT);
    }
}

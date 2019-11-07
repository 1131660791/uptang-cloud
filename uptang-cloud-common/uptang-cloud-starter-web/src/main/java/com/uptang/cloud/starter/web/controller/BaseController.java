package com.uptang.cloud.starter.web.controller;

import com.uptang.cloud.core.exception.AuthenticationException;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.core.util.StringUtils;
import com.uptang.cloud.pojo.domain.context.UserContextThreadLocal;
import com.uptang.cloud.pojo.domain.user.UserContext;
import com.uptang.cloud.pojo.enums.UserTypeEnum;
import com.uptang.cloud.starter.common.enums.ResponseCodeEnum;
import com.uptang.cloud.starter.web.Constants;
import com.uptang.cloud.starter.web.util.HostUtils;
import com.uptang.cloud.starter.web.util.UserTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public abstract class BaseController {
    @Autowired
    protected HttpServletRequest httpRequest;

    @Autowired
    protected UserTokenUtils userTokenUtils;


    /**
     * 获取登录用户的信息
     *
     * @return 登录用户信息
     * @throws BusinessException 授权令牌过期了
     */
    protected UserContext getUserContext() throws BusinessException {
        return Optional.ofNullable(getUserContextSafely())
                .orElseThrow(() -> new BusinessException(ResponseCodeEnum.TOKEN_EXPIRED.getCode(), "授权令牌已过期"));
    }

    /**
     * 根据登录用户获取用户ID
     *
     * @return 用户ID
     */
    protected Long getUserId() throws BusinessException {
        return 123L;
        //        return Optional.ofNullable(getUserContextSafely()).map(UserContext::getUserId)
//                .orElseThrow(() -> new BusinessException(ResponseCodeEnum.TOKEN_EXPIRED.getCode(), "授权令牌已过期"));
    }

    /**
     * 获取登录用户的信息
     *
     * @return 登录用户信息
     */
    protected UserContext getUserContextSafely() {
        UserContext userContext = UserContextThreadLocal.get();
        if (null != userContext) {
            return userContext;
        }

        // 根据 Token 获取用户信息
        String token = getTokenSafely();
        return StringUtils.isBlank(token) ? null : userTokenUtils.getUserContext(token);
    }

    /**
     * 根据登录用户获取用户ID
     *
     * @return 用户ID
     */
    protected Long getUserIdSafely() {
        return Optional.ofNullable(getUserContextSafely()).map(UserContext::getUserId).orElse(null);
    }

    /**
     * 根据请求获取IP
     *
     * @return 请求IP
     */
    protected String getRequestHost() {
        return getRequestHost(getRequest());
    }

    /**
     * 根据请求获取IP
     *
     * @param request HttpServletRequest
     * @return 请求IP
     */
    protected String getRequestHost(HttpServletRequest request) {
        request = Optional.ofNullable(request).orElse(httpRequest);
        return null == request ? StringUtils.EMPTY : HostUtils.getRequestIp(request);
    }


    /**
     * 根据用户请求信息获取Token & RequestIP
     *
     * @return token
     */
    protected String getToken() throws AuthenticationException {
        HttpServletRequest request = Optional.ofNullable(httpRequest).orElse(getRequest());
        if (null == request) {
            throw new AuthenticationException("授权令牌不能不空");
        }

        // 获取授权令牌
        String token = StringUtils.trimToEmpty(request.getHeader(Constants.TOKEN_PARA_NAME));
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("授权令牌不能不空");
        }

        return token;
    }

    /**
     * 根据用户请求信息获取Token & RequestIP
     *
     * @return token
     */
    protected String getTokenSafely() {
        // 获取授权令牌
        return Optional.ofNullable(Optional.ofNullable(httpRequest).orElse(getRequest()))
                .map(req -> req.getHeader(Constants.TOKEN_PARA_NAME))
                .orElse(StringUtils.EMPTY);
    }

    /**
     * 将URL上的字符串进行解码
     *
     * @param searchTerm 查询的字条串
     * @return 解码后的字符串
     */
    protected String decodeString(String searchTerm) {
        if (StringUtils.isNotBlank(searchTerm)) {
            try {
                return URLDecoder.decode(searchTerm.trim(), Constants.DEFAULT_CHAR_SET_VALUE);
            } catch (UnsupportedEncodingException ex) {
                log.warn("字符串(" + searchTerm + ")解码错误", ex);
            }
        }
        return StringUtils.EMPTY;
    }


    /**
     * @return HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 是否是调试模式
     */
    protected boolean isDebug() {
        return Boolean.TRUE.toString().equalsIgnoreCase(Optional.ofNullable(httpRequest).orElse(getRequest()).getParameter(Constants.PARA_DEBUG));
    }

    /**
     * @return 获取用户身份
     */
    protected UserTypeEnum getUserType() {
        return Optional.ofNullable(getUserContextSafely())
                .map(context -> UserTypeEnum.parse(context.getUserType()))
                .orElse(UserTypeEnum.STUDENT);
    }
}

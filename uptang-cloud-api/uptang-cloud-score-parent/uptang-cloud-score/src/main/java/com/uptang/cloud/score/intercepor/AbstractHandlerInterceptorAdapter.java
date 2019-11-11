package com.uptang.cloud.score.intercepor;

import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.starter.web.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 15:55
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 * AnnotationUtils.findAnnotation(((HandlerMethod) handler).getMethod(), HogeHogeAnnotation.class)
 */
public abstract class AbstractHandlerInterceptorAdapter extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        if (StringUtils.isBlank(getToken(request))) {
            throw new BusinessException("Token is require");
        }

        return preHandle(request, response, (HandlerMethod) handler);
    }


    /**
     * 获取Token
     *
     * @param request
     * @return
     */
    String getToken(HttpServletRequest request) {
        return request.getHeader(Constants.TOKEN_PARA_NAME);
    }


    /**
     * 目标Handler是否包含注解
     *
     * @param handler
     * @param annotation
     * @return
     */
    boolean hasAnnotation(HandlerMethod handler, Class<? extends Annotation> annotation) {
        return AnnotationUtils.findAnnotation(handler.getMethod(), annotation) != null;
    }


    /**
     * 拦截器前置处理器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
}

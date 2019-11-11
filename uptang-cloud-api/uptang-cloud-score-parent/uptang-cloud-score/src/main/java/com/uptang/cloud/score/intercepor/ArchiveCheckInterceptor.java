package com.uptang.cloud.score.intercepor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.score.annotation.ArchiveCheck;
import com.uptang.cloud.score.service.IRestCallerService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 15:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
public class ArchiveCheckInterceptor extends AbstractHandlerInterceptorAdapter {

    private final ObjectMapper mapper;

    private final IRestCallerService restCallerService;

    public ArchiveCheckInterceptor(ObjectMapper mapper, IRestCallerService restCallerService) {
        this.mapper = mapper;
        this.restCallerService = restCallerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        return hasAnnotation(handler, ArchiveCheck.class);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }
}



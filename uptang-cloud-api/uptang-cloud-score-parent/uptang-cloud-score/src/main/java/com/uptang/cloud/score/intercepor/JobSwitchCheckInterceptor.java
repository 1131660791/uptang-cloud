package com.uptang.cloud.score.intercepor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.score.annotation.JobSwitchCheck;
import com.uptang.cloud.score.service.IRestCallerService;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 12:08
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : 任务是否开放拦截器
 */
public class JobSwitchCheckInterceptor extends AbstractHandlerInterceptorAdapter {

    private final ObjectMapper mapper;

    private final IRestCallerService restCallerService;

    public JobSwitchCheckInterceptor(ObjectMapper mapper,
                                     IRestCallerService restCallerService) {
        this.mapper = mapper;
        this.restCallerService = restCallerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        return hasAnnotation(handler, JobSwitchCheck.class);
    }

    /**
     * 解析请求参数 发送Rest请求
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // Header
        // query string
        // body
        System.out.println(Utils.getPayload(request));
        System.out.println(handler);
//        ModuleSwitchDto moduleSwitch = ModuleSwitchDto.builder().gradeId(gradeId).build();
//        moduleSwitch.setToken(request.getHeader(Constants.TOKEN_PARA_NAME));
//        ModuleSwitchResponseDto moduleSwitchResponse = restCallerService.moduleSwitch(moduleSwitch);
//        if (moduleSwitchResponse != null && moduleSwitchResponse.getSwitched()) {
//            log.info("任务是否开放 ==> {}", moduleSwitch);
//        }
//        restCallerService.moduleSwitch()

    }
}

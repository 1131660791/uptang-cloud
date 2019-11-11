package com.uptang.cloud.score.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.score.intercepor.ArchiveCheckInterceptor;
import com.uptang.cloud.score.intercepor.JobSwitchCheckInterceptor;
import com.uptang.cloud.score.service.IRestCallerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 14:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Configuration
public class CustomizeWebMvcConfiguration implements WebMvcConfigurer {

    private final ObjectMapper mapper;

    private final IRestCallerService restCallerService;

    public CustomizeWebMvcConfiguration(ObjectMapper mapper, IRestCallerService restCallerService) {
        this.mapper = mapper;
        this.restCallerService = restCallerService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JobSwitchCheckInterceptor(mapper, restCallerService));
        registry.addInterceptor(new ArchiveCheckInterceptor(mapper, restCallerService));
    }
}

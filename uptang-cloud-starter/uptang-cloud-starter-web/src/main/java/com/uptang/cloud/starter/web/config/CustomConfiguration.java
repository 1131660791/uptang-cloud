package com.uptang.cloud.starter.web.config;

import com.uptang.cloud.starter.web.interceptor.UserContextInterceptor;
import com.uptang.cloud.starter.web.json.JsonResultHandler;
import com.uptang.cloud.starter.web.util.UserTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@Aspect
@Configuration
public class CustomConfiguration implements WebMvcConfigurer {
    private static final String DEV_ENV = "DEV";

    /**
     * 获取当前环境
     */
    @Value("${spring.profiles:DEV}")
    private String springProfiles;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (StringUtils.containsIgnoreCase(springProfiles, DEV_ENV)) {
            registry.addMapping("/**")
                    .allowedMethods(
                            RequestMethod.GET.name(),
                            RequestMethod.POST.name(),
                            RequestMethod.PUT.name(),
                            RequestMethod.DELETE.name(),
                            RequestMethod.OPTIONS.name()
                    ).allowedOrigins("*");
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 解析 Token
        registry.addInterceptor(new UserContextInterceptor(userTokenUtils()))
                .addPathPatterns("/**").excludePathPatterns("/inner/**");
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonResultHandler());
    }

    /**
     * 对JSON结果进行处理
     */
    @Bean
    public JsonResultHandler jsonResultHandler() {
        return new JsonResultHandler();
    }

    /**
     * 内部服务调用采用二进制方式传输数据
     */
    /*
    @Bean
    public CustomHttpMessageConverter customHttpMessageConverter() {
        return new CustomHttpMessageConverter();
    }*/

    @Bean
    public UserTokenUtils userTokenUtils() {
        return new UserTokenUtils();
    }
}

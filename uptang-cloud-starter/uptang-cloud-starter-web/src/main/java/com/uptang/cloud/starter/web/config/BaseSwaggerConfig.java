package com.uptang.cloud.starter.web.config;

import com.uptang.cloud.starter.web.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Configuration
public abstract class BaseSwaggerConfig implements WebMvcConfigurer {
    /**
     * 需要扫描 controller 的基础包名
     *
     * @return String
     */
    protected abstract Package getBasePackage();

    /**
     * 对文档的描述
     *
     * @return ApiInfo
     */
    protected abstract ApiInfo getApiInfo();


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Bean
    public Docket allApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("所有接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage(getBasePackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getGlobalHeaderParameters())
                .apiInfo(getApiInfo());
    }

    /**
     * 增加全局默认参数
     *
     * @return 参数列表
     */
    private final List<Parameter> getGlobalHeaderParameters() {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name(Constants.TOKEN_PARA_NAME).description("授权令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(false)
                .defaultValue(RandomStringUtils.randomAlphanumeric(16)).build());

        parameters.add(new ParameterBuilder()
                .name(Constants.PARA_DEBUG).description("用作调试，如果为true则不验证令牌和权限")
                .modelRef(new ModelRef("boolean")).parameterType("query").required(false)
                .defaultValue("false").build());

        return parameters;
    }
}

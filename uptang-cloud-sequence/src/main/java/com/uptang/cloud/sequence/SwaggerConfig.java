package com.uptang.cloud.sequence;

import com.uptang.cloud.starter.web.config.BaseSwaggerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    protected Package getBasePackage() {
        return SwaggerConfig.class.getPackage();
    }

    @Override
    protected ApiInfo getApiInfo() {
        return new ApiInfoBuilder().title("全局序列")
                .version("4.0.0")
                .contact(new Contact("兴唐技术", "", "dev@uptang.com.cn"))
                .build();
    }
}
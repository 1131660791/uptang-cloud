package com.uptang.cloud.base;

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
 * @date 2019-09-30
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
        return new ApiInfoBuilder().title("基础数据")
                .version("4.0.0")
                .contact(new Contact("兴唐技术", "", "dev@uptang.com.cn"))
                .build();
    }

    /*
    @Bean
    public Docket attachmentApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("附件接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage(SwaggerConfig.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getGlobalHeaderParameters())
                .apiInfo(getApiInfo());
    }
    */
}
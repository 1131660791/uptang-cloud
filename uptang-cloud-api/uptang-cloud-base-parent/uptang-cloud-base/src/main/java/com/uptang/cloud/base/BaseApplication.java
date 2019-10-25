package com.uptang.cloud.base;

import com.uptang.cloud.starter.web.UptangCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 基础数据服务
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@UptangCloudApplication
@MapperScan("com.uptang.cloud.**.repository")
@EnableFeignClients(basePackages = "com.uptang.cloud")
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}

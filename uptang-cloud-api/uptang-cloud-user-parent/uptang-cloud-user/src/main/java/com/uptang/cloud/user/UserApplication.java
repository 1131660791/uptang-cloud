package com.uptang.cloud.user;

import com.uptang.cloud.starter.web.UptangCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户基础服务
 *
 * @author cht
 * @date 2019-11-19
 */
@UptangCloudApplication
@MapperScan("com.uptang.cloud.**.repository")
@EnableFeignClients(basePackages = "com.uptang.cloud")
@EnableCircuitBreaker
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

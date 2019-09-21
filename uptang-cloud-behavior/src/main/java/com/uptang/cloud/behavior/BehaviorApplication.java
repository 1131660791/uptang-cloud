package com.uptang.cloud.behavior;

import com.uptang.cloud.starter.web.UptangCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户行为收集服务
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@UptangCloudApplication
@MapperScan("com.uptang.cloud.**.repository")
@EnableFeignClients(basePackages = "com.uptang.cloud.provider")
public class BehaviorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehaviorApplication.class, args);
    }
}

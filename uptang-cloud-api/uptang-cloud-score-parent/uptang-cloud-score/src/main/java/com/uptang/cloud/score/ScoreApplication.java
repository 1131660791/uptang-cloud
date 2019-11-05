package com.uptang.cloud.score;

import com.uptang.cloud.starter.web.UptangCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 成绩管理服务
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@UptangCloudApplication
@MapperScan("com.uptang.cloud.**.repository")
@EnableFeignClients(basePackages = "com.uptang.cloud")
public class ScoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreApplication.class, args);
    }
}

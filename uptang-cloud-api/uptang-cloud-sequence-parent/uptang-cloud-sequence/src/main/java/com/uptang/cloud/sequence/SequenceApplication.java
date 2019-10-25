package com.uptang.cloud.sequence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class SequenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SequenceApplication.class, args);
    }
}

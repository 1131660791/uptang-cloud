package com.uptang.cloud.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 试卷图片处理
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Slf4j
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.uptang.cloud")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class PaperImageTask implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(PaperImageTask.class, args);
    }

    @Override
    public void run(String... args) {
    }
}

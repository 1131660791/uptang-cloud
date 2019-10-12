package com.uptang.cloud.task;

import com.uptang.cloud.task.mode.PaperScan;
import com.uptang.cloud.task.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 试卷图片处理
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-08
 */
@Slf4j
@EnableAsync
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.uptang.cloud")
@ComponentScan(basePackages = "com.uptang.cloud")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class PaperImageTask implements CommandLineRunner {
    @Autowired
    private PaperRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(PaperImageTask.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String examCode = "xty_20190617150608344";
        int prevId = 0, count = 100;
        List<PaperScan> papers;
        do {
            papers = repository.getPapers(examCode, prevId, 100);
            if (CollectionUtils.isNotEmpty(papers)) {
                prevId = papers.get(papers.size() - 1).getId();
                papers.forEach(paper -> {
                    log.error("Exam:{}, {}", examCode, paper);
                });
            }
            TimeUnit.SECONDS.sleep(5);
        } while (papers.size() >= count);
    }
}

package com.uptang.cloud.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.obs.services.ObsClient;
import com.uptang.cloud.base.common.support.PaperImageProcessor;
import com.uptang.cloud.task.util.CacheKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

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
        sendTasks();
        // uploadTestFiles();
    }

    // ====================== 以下是模拟发布考试 ======================
    private void sendTasks() {
        String cacheKey = CacheKeys.getExamExtractTaskKey();
        redisTemplate.opsForSet().add(cacheKey, "xty_20191011112438446");
        // redisTemplate.opsForSet().add(cacheKey, "xty_20190927140900970", "xty_20190617150608344", "xty_20191011112438446", "xty_20190927140900970", "uptang_base", "xty");
        //redisTemplate.opsForSet().add(cacheKey,  "uptang_base", "xty");
    }


    // ====================== 以下是测试批量上传图片 ======================
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private PaperImageProcessor processor;


    private void uploadTestFiles() {
        final File[] files = new File("D:\\big-data\\images\\2").listFiles();
        if (ArrayUtils.isEmpty(files)) {
            return;
        }

        final int maxThreadCount = 10;
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10_000);
        final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(maxThreadCount,
                maxThreadCount * 2, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("HW-UP-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        // 产生ID
        int startId = 30000, imageCount = 10000;
        threadPool.execute(() -> {
            IntStream.iterate(startId, i -> i + 1).limit(imageCount).forEach(id -> {
                try {
                    queue.put(id);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            });
        });


        final int fileCount = files.length;
        final String bucketName = "uptang-cloud-test";
        final ObsClient client = processor.getObsClient();

        LongAdder counter = new LongAdder();
        IntStream.range(0, maxThreadCount - 1).forEach(idx -> {
            try {
                Integer id = queue.take();
                String fileName = StringUtils.leftPad(String.valueOf(id), 7, '0');
                String fullName = StringUtils.leftPad(String.valueOf(id % 1000), 3, '0') + '/' + fileName + ".jpg";

                File file = files[id % fileCount];
                client.putObject(bucketName, fullName, file);
                counter.add(1);

                log.info("count: {}, file: {}, size: {}", counter.intValue(), fullName, file.length());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
    }
}

package com.uptang.cloud.task.process;

import com.uptang.cloud.core.util.CollectionUtils;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.core.util.StringUtils;
import com.uptang.cloud.task.mode.PaperScan;
import com.uptang.cloud.task.repository.PaperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-12
 */
@Slf4j
@Component
public class PaperImageExtractor {
    private static final String EXAM_TASK_KEY = "task:exam:paper";
    private static final String EXAM_PROGRESS_KEY = "task:exam:progress";

    /**
     * 正在执行的考试任务
     */
    private final Set<String> RUNNING_EXAMS = new CopyOnWriteArraySet<>();

    private final PaperRepository repository;
    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public PaperImageExtractor(PaperRepository repository, StringRedisTemplate redisTemplate, ThreadPoolTaskExecutor taskExecutor) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
        this.taskExecutor = taskExecutor;
    }


    @Scheduled(initialDelay = 1_000, fixedDelay = 5_000)
    public void checkExamTask() {
        Set<String> exams = redisTemplate.opsForSet().members(EXAM_TASK_KEY);
        if (CollectionUtils.isEmpty(exams)) {
            log.warn("没有需要处理的考试任务！");
            return;
        }

        // 将正在处理的任务排除
        exams.removeIf(RUNNING_EXAMS::contains);
        if (CollectionUtils.isEmpty(exams)) {
            log.info("所有考试任务正在处理中...");
            return;
        }

        // 开始多线程提取考试任务
        exams.forEach(exam -> {
            log.info("开始处理考试任务: {}", exam);
            taskExecutor.submit(new ExtractTask(exam));
        });
    }


    /**
     * 任务处理器
     */
    class ExtractTask implements Runnable {
        private final String examCode;
        private LongAdder counter = new LongAdder();

        ExtractTask(String examCode) {
            this.examCode = examCode;
        }

        @Override
        public void run() {
            // 将任务标记为正在执行
            RUNNING_EXAMS.add(examCode);

            // 获取恢复点
            String cacheKey = getCrashPointCacheKey(examCode);
            String crashPoint = redisTemplate.opsForValue().get(cacheKey);

            int prevId = 0, count = 100;
            if (StringUtils.isNotBlank(cacheKey)) {
                prevId = NumberUtils.toInt(crashPoint, prevId);
            }

            List<PaperScan> papers = new ArrayList<>(0);
            do {
                try {
                    papers = repository.getPapers(examCode, prevId, 100);
                    if (CollectionUtils.isNotEmpty(papers)) {
                        prevId = papers.get(papers.size() - 1).getId();
                        papers.forEach(paper -> {
                            log.error("Exam:{}, {}", examCode, paper);
                        });

                        counter.add(papers.size());

                        // 设置恢复点
                        redisTemplate.opsForValue().set(cacheKey, String.valueOf(prevId), 7, TimeUnit.DAYS);

                        // 设置进度
                        redisTemplate.opsForHash().increment(EXAM_PROGRESS_KEY, examCode, papers.size());
                        log.info("Number of {} papers processed for exam: {}", counter.intValue(), examCode);
                    }

                    // TODO for test only
                    // TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                } catch (Exception ex) {
                    log.error("处理任务:{}失败！{}", examCode, ex.getMessage());
                }
            } while (papers.size() >= count);

            // 任务处理完成
            redisTemplate.opsForSet().remove(EXAM_TASK_KEY, examCode);

            // 还需要记录处理进度
            log.info("All of papers({}) are processed  for exam {}", counter.intValue(), examCode);
        }
    }

    private static String getCrashPointCacheKey(String examCode) {
        return "task:exam:crash:" + StringUtils.trimToEmpty(examCode).trim();
    }
}

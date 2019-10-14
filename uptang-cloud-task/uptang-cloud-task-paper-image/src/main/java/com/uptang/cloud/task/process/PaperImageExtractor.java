package com.uptang.cloud.task.process;

import com.alibaba.fastjson.JSON;
import com.uptang.cloud.base.common.domain.PaperImage;
import com.uptang.cloud.base.common.domain.PaperImageFormat;
import com.uptang.cloud.base.common.domain.PaperImageSource;
import com.uptang.cloud.core.util.CollectionUtils;
import com.uptang.cloud.core.util.NumberUtils;
import com.uptang.cloud.core.util.StringUtils;
import com.uptang.cloud.task.mode.PaperFormat;
import com.uptang.cloud.task.mode.PaperScan;
import com.uptang.cloud.task.repository.PaperFormatRepository;
import com.uptang.cloud.task.repository.PaperRepository;
import com.uptang.cloud.task.util.CacheKeys;
import com.uptang.cloud.task.util.PaperFormatParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-12
 */
@Slf4j
@Component
public class PaperImageExtractor {
    /**
     * 每隔五分钟检查一次，是否有新的考试项目需要处理
     */
    private static final int TASK_INTERVAL_MILLS = 1000 * 60 * 5;

    /**
     * 每次提取试卷的数量
     */
    private static final int TASK_FETCH_SIZE = 500;

    /**
     * 正在执行的考试任务
     */
    private final Set<String> RUNNING_EXAMS = new CopyOnWriteArraySet<>();

    private final PaperRepository paperRepository;
    private final PaperFormatRepository formatRepository;
    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    public PaperImageExtractor(PaperRepository paperRepository, PaperFormatRepository formatRepository,
                               StringRedisTemplate redisTemplate, ThreadPoolTaskExecutor taskExecutor) {
        this.paperRepository = paperRepository;
        this.formatRepository = formatRepository;
        this.redisTemplate = redisTemplate;
        this.taskExecutor = taskExecutor;
    }


    /**
     * 只允许一台机器执行
     */
    @Scheduled(initialDelay = 1000, fixedDelay = TASK_INTERVAL_MILLS)
    public void checkExamTask() {
        Set<String> examCodes = redisTemplate.opsForSet().members(CacheKeys.getExamExtractTaskKey());
        if (CollectionUtils.isEmpty(examCodes)) {
            log.warn("No exam to process!");
            return;
        }

        // 将正在处理的任务排除
        examCodes.removeIf(RUNNING_EXAMS::contains);
        if (CollectionUtils.isEmpty(examCodes)) {
            log.info("All exam are processing...");
            return;
        }

        // 开始多线程提取考试任务
        examCodes.forEach(examCode -> {
            log.info("Start process exam {}", examCode);
            taskExecutor.submit(new ExtractTask(examCode));
        });
    }

    /**
     * 获取考试题目的格式
     *
     * @param examCode 考试代码
     * @return 题目格式
     */
    private Map<String, List<PaperImageFormat>> getItemFormatMap(String examCode) {
        try {
            List<PaperFormat> formats = formatRepository.getAllFormats(examCode);
            if (CollectionUtils.isEmpty(formats)) {
                log.warn("Can not find any formats for exam {}", examCode);
                return null;
            }

            List<PaperImageFormat> paperImageFormats = formats.stream()
                    .map(itemFormat -> {
                        PaperImageFormat format = PaperFormatParser.parse(itemFormat.getFormat());
                        Optional.ofNullable(format).ifPresent(f -> format.setItemNum(itemFormat.getItemNum()));
                        return format;
                    }).filter(Objects::nonNull).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(paperImageFormats)) {
                log.warn("Failed to parse item format for exam {}", examCode);
                return null;
            }
            PaperFormatParser.updateFilenameSuffix(paperImageFormats, "a", "b");

            // 按题目分组
            return paperImageFormats.stream().collect(Collectors.groupingBy(PaperImageFormat::getItemNum));
        } catch (Exception ex) {
            log.warn("Failed to parse format of exam {}", examCode);
            return null;
        }
    }

    /**
     * 处理试卷参数，将其发送至消息队列
     *
     * @param examCode             考试代码
     * @param papers               答题卡
     * @param itemGroupedFormatMap 题目格式
     */
    private void processPapers(String examCode, List<PaperScan> papers, Map<String, List<PaperImageFormat>> itemGroupedFormatMap) {
        for (PaperScan paper : papers) {
            log.debug("Process paper {} of exam {}", paper, examCode);
            if (StringUtils.isBlank(paper.getImagePath())) {
                continue;
            }

            itemGroupedFormatMap.forEach((itemNum, formats) -> {
                PaperImage paperImage = PaperImage.builder()
                        .vertically(true).studentId(paper.getTicketNumber())
                        .examCode(examCode).subjectCode(paper.getSubjectCode()).itemNum(itemNum)
                        .build();

                paperImage.setSources(formats.stream().map(format -> {
                    return PaperImageSource.builder()
                            .path(getFileName(paper.getImagePath(), format.getFilenameSuffix()))
                            .x(format.getX()).y(format.getY())
                            .width(format.getWidth()).height(format.getHeight())
                            .build();
                }).collect(Collectors.toList()));

                // TODO 向消息队列中发消息
                String message = JSON.toJSONString(paperImage);
                log.info("开始发送消息: {}", message);
            });
        }
    }

    /**
     * 格式格式文件，重新计算图片文件路径
     *
     * @param fileName 原文件名
     * @param suffix   文件名后缀
     * @return 文件名
     */
    private String getFileName(String fileName, String suffix) {
        return FilenameUtils.getFullPath(fileName) + FilenameUtils.getBaseName(fileName)
                + '_' + suffix + '.' + FilenameUtils.getExtension(fileName);
    }


    /**
     * 任务处理器
     */
    class ExtractTask implements Runnable {
        private final String examCode;
        private final LongAdder counter = new LongAdder();

        ExtractTask(String examCode) {
            this.examCode = examCode;
        }

        @Override
        public void run() {
            StopWatch stopWatch = new StopWatch("extract paper");

            // 缓存Key
            String crashKey = CacheKeys.getExamExtractCrashPointKey(examCode);

            // 每个考试项目只允许一个线程提取数据，采用Redis进行分布式锁
            String lockKey = crashKey + "_mutex", lockVal = String.valueOf(System.currentTimeMillis());
            try {
                Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockVal, 1, TimeUnit.DAYS);
                if (!Optional.ofNullable(locked).orElse(false)) {
                    log.info("Other task is processing exam {}", examCode);
                    return;
                }

                // 解析试卷格式文件
                String taskKey = CacheKeys.getExamExtractTaskKey();
                Map<String, List<PaperImageFormat>> itemGroupedFormatMap = getItemFormatMap(examCode);
                if (MapUtils.isEmpty(itemGroupedFormatMap)) {
                    log.warn("Failed to parse format of exam {}", examCode);
                    redisTemplate.opsForSet().remove(taskKey, examCode);
                    return;
                }

                String progressKey = CacheKeys.getExamExtractProgressKey();

                // 将任务标记为正在执行
                RUNNING_EXAMS.add(examCode);

                // 获取恢复点
                String crashPoint = redisTemplate.opsForValue().get(crashKey);
                int prevId = StringUtils.isBlank(crashKey) ? 0 : NumberUtils.toInt(crashPoint, 0);
                List<PaperScan> papers = new ArrayList<>(0);
                do {
                    stopWatch.start(String.format("Extract Count:%s, StartID:%s", TASK_FETCH_SIZE, prevId));
                    try {
                        papers = paperRepository.getPapers(examCode, prevId, TASK_FETCH_SIZE);
                        if (CollectionUtils.isEmpty(papers)) {
                            continue;
                        }

                        // 下次遍历数据的开始点
                        prevId = papers.get(papers.size() - 1).getId();

                        // 准备裁切参数，将其发送到消息队列
                        processPapers(examCode, papers, itemGroupedFormatMap);

                        // 设置恢复点
                        redisTemplate.opsForValue().set(crashKey, String.valueOf(prevId), 7, TimeUnit.DAYS);

                        // 设置进度
                        redisTemplate.opsForHash().increment(progressKey, examCode, papers.size());

                        counter.add(papers.size());
                        log.info("Number of {} papers processed for exam: {}. It's took: {}ms",
                                counter.intValue(), examCode, stopWatch.getLastTaskTimeMillis());
                    } catch (Exception ex) {
                        log.error("Failed to extract {}. {}", examCode, ex.getMessage());
                    } finally {
                        stopWatch.stop();
                    }
                } while (papers.size() >= TASK_FETCH_SIZE);

                // 任务处理完成
                redisTemplate.opsForSet().remove(taskKey, examCode);
                log.info("All of papers({}) are processed for exam {}. It's took: {}ms\n{}",
                        counter.intValue(), examCode, stopWatch.getTotalTimeMillis(), stopWatch.prettyPrint());
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            } finally {
                redisTemplate.delete(lockKey);
            }
        }
    }
}

package com.uptang.cloud.starter.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 统一线程池，或异步调用
 * <p>
 * 异步调用接口示例
 * <pre> {@code
 *
 *  @Async("taskExecutor")
 *  public void method() {
 *   // do something
 *  }
 *
 *  或者
 *
 *  @Autowired
 *  @Qualifier("taskExecutor")
 *  private final ThreadPoolTaskExecutor taskExecutor;
 *
 *  taskExecutor.execute(() -> log.error("线程池运行：{}", idx));
 * </pre>
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 在线程中仍然可以使用上下文信息
        executor.setTaskDecorator(new ContextDecorator());

        // 核心线程数1：线程池创建时候初始化的线程数
        executor.setCorePoolSize(1);

        // 最大线程数10：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);

        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(200);

        // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);

        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("XT-POOL-");

        // 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean，这样这些异步任务的销毁就会先于Redis线程池的销毁
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(60);

        return executor;
    }

    /**
     * 拷贝上下文信息
     */
    static class ContextDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            return () -> {
                try {
                    RequestContextHolder.setRequestAttributes(context);
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}


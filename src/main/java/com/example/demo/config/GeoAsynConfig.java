package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class GeoAsynConfig {

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(8);
        // 设置最大线程数，当核心线程数满了且队列满了，会创建新的线程，直到达到最大线程数。而当前队列是无界队列，所以不会满，此处设置无效。直接使用默认值，无限大
        //executor.setMaxPoolSize(16);
        // 设置队列容量,默认为Integer.MAX_VALUE
        //executor.setQueueCapacity(10000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("geo-admin-thread-");
        // 设置拒绝策略。无界队列不会出现满的情况，所以不配置存储策略。直接抛出 RejectedExecutionException 异常
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}

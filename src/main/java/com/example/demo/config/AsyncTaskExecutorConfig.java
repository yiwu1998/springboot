package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncTaskExecutorConfig {

    private Integer autoReplyForPromptPaymentExecutorCoreSize = 10;

    private Integer autoReplyForPromptPaymentExecutorMaxSize = 20;

    private Integer autoReplyForPromptPaymentExecutorQueueCapacity = 1000;

    private Integer autoReplyForPromptPaymentExecutorAliveSeconds = 60;


    @Bean(value = "admitExecutorr")
    public ThreadPoolTaskExecutor getMessageExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(autoReplyForPromptPaymentExecutorCoreSize);
        executor.setMaxPoolSize(autoReplyForPromptPaymentExecutorMaxSize);
        executor.setQueueCapacity(autoReplyForPromptPaymentExecutorQueueCapacity);
        executor.setKeepAliveSeconds(autoReplyForPromptPaymentExecutorAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("admitExecutorr-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

}
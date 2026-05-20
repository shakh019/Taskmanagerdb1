package com.sharafatdin.talgatbek.taskmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/** @author Talgatbek Sharafatdin */
@Configuration
@EnableAsync
public class TalgatbekAsyncConfig {

    @Value("${app.async.core-pool-size:5}")
    private int corePoolSize;
    @Value("${app.async.max-pool-size:10}")
    private int maxPoolSize;
    @Value("${app.async.queue-capacity:100}")
    private int queueCapacity;

    @Bean(name = "talgatbekTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("TalgatbekAsync-");
        executor.initialize();
        return executor;
    }
}

package com.example.config;

import com.example.properties.SwaggerProperties;
import com.example.properties.TaskThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Configuration
// 开启异步
@EnableAsync
public class TaskThreadPoolConfig {

    @Autowired
    private TaskThreadPoolProperties taskThreadPoolProperties;

    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(taskThreadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(taskThreadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(taskThreadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(taskThreadPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(taskThreadPoolProperties.getThreadNamePrefix());

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}

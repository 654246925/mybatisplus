package com.example.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource("classpath:otherproperties/taskthreadpool.properties")
@Configuration
public class TaskThreadPoolProperties {

    @Value("${corePoolSize}")
    private int corePoolSize;

    @Value("${maxPoolSize}")
    private int maxPoolSize;

    @Value("${queueCapacity}")
    private int queueCapacity;

    @Value("${keepAliveSeconds}")
    private int keepAliveSeconds;

    @Value("${threadNamePrefix}")
    private String threadNamePrefix;


}

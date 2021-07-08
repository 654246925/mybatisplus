package com.example.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource("classpath:otherproperties/redisexpirs.properties")
@Configuration
public class RedisExpirsProperties {

    /**
     * 默认expiretimeDefault过期时间
     */
    @Value("${expiretime_default}")
    private Long expiretimeDefault;

    /**
     * expiretimeCommon
     */
    @Value("${expiretime_common}")
    private Long expiretimeCommon;

    /**
     * 默认token过期时间
     */
    @Value("${expiretime_token}")
    private Long expiretimeToken;

}

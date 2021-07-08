package com.example.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@PropertySource("classpath:otherproperties/swagger.properties")
@Configuration
public class SwaggerProperties {
    /**
     * 是否开启swagger，生产环境配置关闭
     */
    @Value("${enable}")
    private Boolean enable;

}

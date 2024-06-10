package com.heyu.train.common.config;
 
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Feign配置类
 * </P>
 * @author Kk
 * @since 2022/4/9 15:54
 */
@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS; //设置日志级别为FULL
    }
}
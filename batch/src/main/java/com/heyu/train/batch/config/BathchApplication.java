package com.heyu.train.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 13:41
 */
@Slf4j
@ComponentScan("com.heyu")
@SpringBootApplication
public class BathchApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext app = SpringApplication.run(BathchApplication.class, args);
        ConfigurableEnvironment environment = app.getEnvironment();
        log.info("启动成功!!地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}

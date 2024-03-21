package com.heyu.train.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 23:55
 */
@Slf4j
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        ConfigurableEnvironment environment = app.run(args).getEnvironment();
        log.info("网关服务启动成功！！");
        log.info("Gateway地址: \thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}

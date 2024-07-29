package com.heyu.train.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 15:04
 *
 * @author heyu
 */
@SpringBootApplication
@ComponentScan("com.heyu")
@MapperScan("com.heyu.train.business.mapper")
@EnableFeignClients(basePackages = "com.heyu.train.common.feign")
@EnableCaching
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
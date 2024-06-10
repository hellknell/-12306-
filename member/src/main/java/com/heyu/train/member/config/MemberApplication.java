package com.heyu.train.member.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableFeignClients(basePackages = {"com.heyu.train.common.feign"})
@ComponentScan("com.heyu")
@MapperScan("com.heyu.train.member.mapper")
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}

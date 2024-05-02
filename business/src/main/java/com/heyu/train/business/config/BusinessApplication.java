package com.heyu.train.business.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}

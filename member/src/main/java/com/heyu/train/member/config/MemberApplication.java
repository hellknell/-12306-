package com.heyu.train.member.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 15:04
 * @author  heyu
 */
@SpringBootApplication
@ComponentScan("com.heyu")
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}

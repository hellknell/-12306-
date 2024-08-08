package com.heyu.train.business.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.heyu.train.business.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/4/14 21:42
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    final TestService testService;
    @GetMapping("/hello")
    @SentinelResource("hello")
    public String hello() throws InterruptedException {
        int i = RandomUtil.randomInt(1, 10);
        if(i<=3){
            throw  new RuntimeException();
        }
        return "hello BUSINESS";
    }
    @GetMapping("/hello1")
    @SentinelResource("hello1")
    public String hello1() throws InterruptedException {
        testService.hello2();
        return "hello BUSINESS1";
    }
}

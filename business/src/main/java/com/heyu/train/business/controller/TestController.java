package com.heyu.train.business.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/4/14 21:42
 */
@RestController
@Slf4j
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "hello World";
    }
}

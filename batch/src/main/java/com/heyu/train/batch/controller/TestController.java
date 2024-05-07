package com.heyu.train.batch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 13:46
 */
@RestController
@RequestMapping("/admin")
public class TestController {
    @GetMapping("/test")
    public String hello() {
        return "hello";
    }

}



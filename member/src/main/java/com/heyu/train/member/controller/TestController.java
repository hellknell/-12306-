package com.heyu.train.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.sax.SAXResult;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/21 15:12
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}

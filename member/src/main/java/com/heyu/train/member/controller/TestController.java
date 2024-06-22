package com.heyu.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/6/21 14:30
 */
@RestController
@RefreshScope
public class TestController {
//    @Value("${test.nacos}")
//    private String name;
//
//    @GetMapping("/test")
//    //返回html,交给前端渲染
//    public String test() {
//        return
//                "<!DOCTYPE html>" +
//                        "<html lang=\"en\">" +
//                        "<head>" +
//                        "<meta charset=\"UTF-8\">" +
//                        "<title>Title</title>" +
//                        "</head>" +
//                        "<body>" +
//                        "<div style=\" color:red;font-size:20px;text-align:center  \">Hello, " + name + "!</div>" +
//                        "</body>" +
//                        "</html>";
//    }
}

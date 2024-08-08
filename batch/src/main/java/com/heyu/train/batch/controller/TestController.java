package com.heyu.train.batch.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.heyu.train.batch.feign.BusinessFeign;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/18 1:27
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "测试")
public class TestController {
    final BusinessFeign businessFeign;
    @Operation(summary = "测试")
    @SentinelResource("test")
    @GetMapping("/test")
    public String test() {
        String hello = businessFeign.hello();
        return hello;

    }

}

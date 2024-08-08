package com.heyu.train.business.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/8/8 16:09
 */
@Service
public class TestService {
    @SentinelResource("hello2")
    public void  hello2() throws InterruptedException {
    Thread.sleep(500);
    }
}

package com.heyu.train.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/29 22:01
 */
@Slf4j
@Order(2)
@Component
public class TestFilter2 implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("TestFilter2执行了");
        return  chain.filter(exchange);
    }
}

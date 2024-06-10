package com.heyu.train.config;

import com.heyu.train.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/30 12:18
 */
@Slf4j
@Order(-1)
@Component
public class MemberFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/admin") || path.contains("/member/login") || path.contains("/member/sendCode")) {
            log.info("放行的请求路径:{}", path);
            return chain.filter(exchange);
        } else {
            log.info("需要拦截的请求路径:{}", path);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (token == null || token.isEmpty()) {
            log.error("token为空");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        boolean flag = JwtUtil.validateToken(token);
        if (!flag) {
            log.error("token验证失败");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);

    }
}

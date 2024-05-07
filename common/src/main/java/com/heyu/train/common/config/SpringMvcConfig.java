package com.heyu.train.common.config;

import com.heyu.train.common.interceptor.LogInterceptor;
import com.heyu.train.common.interceptor.MemberHandleIntercetor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/31 20:09
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SpringMvcConfig implements WebMvcConfigurer {
    final MemberHandleIntercetor memberHandleIntercetor;
    final LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(memberHandleIntercetor)
                .addPathPatterns("/**")
                .excludePathPatterns("/member/login", "/member/sendCode")
                .excludePathPatterns("/swagger**/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v3/**")
                .excludePathPatterns("/doc.html");
    }
}

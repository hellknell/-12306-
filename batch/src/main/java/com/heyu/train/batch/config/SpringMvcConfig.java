package com.heyu.train.batch.config;

import com.heyu.train.common.interceptor.LogInterceptor;
import jakarta.annotation.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/8 7:47
 */
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    private LogInterceptor logInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**");
    }
}

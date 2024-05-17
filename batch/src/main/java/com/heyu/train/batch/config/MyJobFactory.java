package com.heyu.train.batch.config;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 18:02
 */
@Component
@RequiredArgsConstructor
public class MyJobFactory extends SpringBeanJobFactory {
    @Resource
    private AutowireCapableBeanFactory beanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}

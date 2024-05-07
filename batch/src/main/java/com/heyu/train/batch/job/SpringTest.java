package com.heyu.train.batch.job;

import cn.hutool.core.date.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 14:49
 */
//Spring定时任务,适合单机应用
@Component
@EnableScheduling
public class SpringTest implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateTime now = DateTime.now();
        System.out.println("定时任务执行了,当前时间:" + now);
    }
}

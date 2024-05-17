package com.heyu.train.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.heyu.train.batch.feign.BusinessFeign;
import com.heyu.train.common.resp.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 14:49
 */
//Spring定时任务,适合单机应用
@Component
@DisallowConcurrentExecution
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class DailyTrainJob implements Job {
    final BusinessFeign businessFeign;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        log.info("生成每日车次开始");
        Date date1 = new Date();
        DateTime dateTime = DateUtil.offsetDay(date1, 15);
        Date date = dateTime.toJdkDate();
        Result<Object> objectResult = businessFeign.genDailyTrain(date);
        log.info("生成每日车次结果:{}", objectResult);
        log.info("生成每日车次结束");
    }
}

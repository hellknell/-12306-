package com.heyu.train.batch.config;

import com.heyu.train.batch.job.SpringTest;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 15:47
 */
@Configuration
public class QuartzConfig {


    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(SpringTest.class).withIdentity("SpringTest","g01").storeDurably().build();

    }
    @Bean
    public Trigger trigger(){
        return TriggerBuilder.newTrigger().forJob(jobDetail()).withIdentity("SpringTest","trigger").startNow().withSchedule(org.quartz.CronScheduleBuilder.cronSchedule("1/2 * * * * ?")).build();
    }
}

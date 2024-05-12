package com.heyu.train.batch.controller;

import com.heyu.train.batch.req.CronReq;
import com.heyu.train.batch.resp.CronJobResp;
import com.heyu.train.common.resp.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 21:56
 */
@Tag(name = "定时任务", description = "定时任务")
@RestController
@RequestMapping("/admin/job")
@RequiredArgsConstructor
@Slf4j
public class JobController {
    final SchedulerFactoryBean schedulerFactoryBean;
    @RequestMapping(value = "/run",method = RequestMethod.POST)
    @Operation(summary = "手动执行任务")
    public Result<Object> run(@RequestBody CronReq cronJobReq) throws SchedulerException {
        String jobClassName = cronJobReq.getName();
        String jobGroupName = cronJobReq.getGroup();
        log.info("手动执行任务开始：{}, {}", jobClassName, jobGroupName);
        schedulerFactoryBean.getScheduler().triggerJob(JobKey.jobKey(jobClassName, jobGroupName));
        return new Result<>();
    }
    @PostMapping(value = "/add")
    @Operation(summary = "创建定时任务")
    public Result add(@RequestBody  CronReq cronReq){
        String jodName = cronReq.getName();
        String jodGroup = cronReq.getGroup();
        String description = cronReq.getDescription();
        String cronExpression = cronReq.getCronExpression();
        log.info("创建定时任务:{},{},{},{}",jodName,jodGroup,description,cronExpression);
        Result res = new Result<>();
        try {
            //调度器实例
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.start();
            //创建任务
            JobDetail jobDetail =JobBuilder.newJob((Class<? extends Job>) Class.forName(jodName)).withIdentity(jodName, jodGroup).build();
            CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger jobTrigger= TriggerBuilder.newTrigger().withDescription(description).withIdentity(jodName, jodGroup).withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail,jobTrigger);

        } catch (SchedulerException e) {
            log.error("创建定时任务失败"+e);
            res.setSuccess(false);
            res.setMsg("创建定时任务失败:调度异常");

        } catch (ClassNotFoundException e) {
            log.error("创建定时任务失败"+e);
            res.setSuccess(false);
            res.setMsg("创建定时任务失败:任务类不存在");
        }

        log.info("创建定时任务结束:{}",res);
        return res;


    }

    @Operation(summary = "暂停定时任务")
    @RequestMapping(value = "/pause",method =  {RequestMethod.POST})
    public Result pause(@RequestBody CronReq cronJobReq) {
        String jobClassName = cronJobReq.getName();
        String jobGroupName = cronJobReq.getGroup();
        log.info("暂停定时任务开始：{}，{}", jobClassName, jobGroupName);
        Result commonResp = new Result();
        try {
            Scheduler sched = schedulerFactoryBean.getScheduler();
            sched.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("暂停定时任务失败:" + e);
            commonResp.setSuccess(false);
            commonResp.setMsg("暂停定时任务失败:调度异常");
        }
        log.info("暂停定时任务结束：{}", commonResp);
        return commonResp;
    }
    @Operation(summary = "重启定时任务")
    @RequestMapping(value = "/resume",method=RequestMethod.POST)
    public Result resume(@RequestBody CronReq cronJobReq) {
        String jobClassName = cronJobReq.getName();
        String jobGroupName = cronJobReq.getGroup();
        log.info("重启定时任务开始：{}，{}", jobClassName, jobGroupName);
        Result commonResp = new Result();
        try {
            Scheduler sched = schedulerFactoryBean.getScheduler();
            sched.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("重启定时任务失败:" + e);
            commonResp.setSuccess(false);
            commonResp.setMsg("重启定时任务失败:调度异常");
        }
        log.info("重启定时任务结束：{}", commonResp);
        return commonResp;
    }
    @Operation(summary =  "重置定时任务")
    @RequestMapping(value = "/reschedule",method =  RequestMethod.PUT)
    public Result reschedule(@RequestBody CronReq cronJobReq) {
        String jobClassName = cronJobReq.getName();
        String jobGroupName = cronJobReq.getGroup();
        String cronExpression = cronJobReq.getCronExpression();
        String description = cronJobReq.getDescription();
        log.info("更新定时任务开始：{}，{}，{}，{}", jobClassName, jobGroupName, cronExpression, description);
        Result commonResp = new Result();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTriggerImpl trigger1 = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
            trigger1.setStartTime(new Date()); // 重新设置开始时间
            CronTrigger trigger = trigger1;
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(description).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            log.error("更新定时任务失败:" + e);
            commonResp.setSuccess(false);
            commonResp.setMsg("更新定时任务失败:调度异常");
        }
        log.info("更新定时任务结束：{}", commonResp);
        return commonResp;
    }
@Operation(summary =  "删除定时任务")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result delete(@RequestBody CronReq cronJobReq) {
        String jobClassName = cronJobReq.getName();
        String jobGroupName = cronJobReq.getGroup();
        log.info("删除定时任务开始：{}，{}", jobClassName, jobGroupName);
        Result commonResp = new Result();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败:" + e);
            commonResp.setSuccess(false);
            commonResp.setMsg("删除定时任务失败:调度异常");
        }
        log.info("删除定时任务结束：{}", commonResp);
        return commonResp;
    }
@Operation(summary =  "查看所有定时任务")
    @RequestMapping(value="/query",method = RequestMethod.GET)
    public Result query() {
        log.info("查看所有定时任务开始");
        Result commonResp = new Result();
        List<CronJobResp> cronJobDtoList = new ArrayList();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    CronJobResp cronJobResp = new CronJobResp();
                    cronJobResp.setName(jobKey.getName());
                    cronJobResp.setGroup(jobKey.getGroup());

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    CronTrigger cronTrigger = (CronTrigger) triggers.get(0);
                    cronJobResp.setNextFireTime(cronTrigger.getNextFireTime());
                    cronJobResp.setPreFireTime(cronTrigger.getPreviousFireTime());
                    cronJobResp.setCronExpression(cronTrigger.getCronExpression());
                    cronJobResp.setDescription(cronTrigger.getDescription());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(cronTrigger.getKey());
                    cronJobResp.setState(triggerState.name());

                    cronJobDtoList.add(cronJobResp);
                }

            }
        } catch (SchedulerException e) {
            log.error("查看定时任务失败:" + e);
            commonResp.setSuccess(false);
            commonResp.setMsg("查看定时任务失败:调度异常");
        }
        commonResp.setData(cronJobDtoList);
        log.info("查看定时任务结束：{}", commonResp);
        return commonResp;
    }
}

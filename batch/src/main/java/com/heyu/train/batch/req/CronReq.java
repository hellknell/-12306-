package com.heyu.train.batch.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/7 21:57
 */
@Data
public class CronReq {
    @Schema(description = "任务组", required = true)
    private String  group;
    @Schema(description = "任务名称", required = true)
    private String name;
    @Schema(description = "任务描述", required = true)
    private  String description;
    @Schema(description = "cron表达式", required = true)
   private String cronExpression;

}

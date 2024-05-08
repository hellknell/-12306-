package com.heyu.train.batch.resp;

import com.heyu.train.common.resp.req.PageReq;
import lombok.Data;
import lombok.Getter;
import org.dom4j.io.SAXReader;

import java.util.Date;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/5/8 7:17
 */
@Data
public class CronJobResp {
    private String name;
    private String group;
    @Getter
    private Date nextFireTime;
    private Date preFireTime;
    private String cronExpression;
    private String description;
    private String state;
}

package com.heyu.train.batch.feign;

import com.heyu.train.common.resp.Result;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/8/8 18:01
 */
@Component
public class BusinessFeignFallBack implements BusinessFeign{
    @Override
    public String hello() {
        return "FallBack";
    }

    @Override
    public Result<Object> genDailyTrain(Date date) {
        return null;
    }
}

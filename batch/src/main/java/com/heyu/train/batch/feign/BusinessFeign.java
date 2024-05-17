package com.heyu.train.batch.feign;

import com.heyu.train.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Component
@FeignClient(name = "business", url = "http://localhost:8002/business")
public interface BusinessFeign {
    @GetMapping("/hello")
    String hello();

    @GetMapping("/admin/daily-train/generate/{date}")
    Result<Object> genDailyTrain(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}

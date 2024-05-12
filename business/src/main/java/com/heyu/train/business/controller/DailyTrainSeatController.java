package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainSeatQueryReq;
import com.heyu.train.business.req.DailyTrainSeatSaveReq;
import com.heyu.train.business.resp.DailyTrainSeatQueryResp;
import com.heyu.train.business.service.DailyTrainSeatService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name= "乘客管理")
@Validated
@RequestMapping("/admin/daily-train-seat")
@RequiredArgsConstructor
public class DailyTrainSeatController {
    final DailyTrainSeatService dailyTrainSeatService;

    @Operation(summary = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainSeatSaveReq req) {
        dailyTrainSeatService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询乘客")
    @GetMapping("/query-list")
    public   Result<PageInfo<DailyTrainSeatQueryResp>> queryList(@Valid DailyTrainSeatQueryReq req) {
        return Result.success(dailyTrainSeatService.queryList(req));

    }
    @Operation(summary = "删除乘客")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        dailyTrainSeatService.del(id);
        return  Result.success();
    }
}

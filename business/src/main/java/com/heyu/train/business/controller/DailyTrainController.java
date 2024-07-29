package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainQueryReq;
import com.heyu.train.business.req.DailyTrainSaveReq;
import com.heyu.train.business.resp.DailyTrainQueryResp;
import com.heyu.train.business.service.DailyTrainService;
import com.heyu.train.common.resp.Result;
import com.heyu.train.generator.generator.help.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/23 17:36
 */
@RestController
@Tag(name = "每日车次管理")
@Validated
@RequestMapping("/admin/daily-train")
@RequiredArgsConstructor
public class DailyTrainController {
    final DailyTrainService dailyTrainService;

    @Operation(summary = "新增/保存乘客")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainSaveReq req) {
        dailyTrainService.save(req);
        return Result.success();
    }

    @Operation(summary = "查询每日火车")
    @GetMapping("/query-list")
    public Result<PageInfo<DailyTrainQueryResp>> queryList(@Valid DailyTrainQueryReq req) {
        return Result.success(dailyTrainService.queryList(req));
    }

    @Operation(summary = "查询每日火车2")
    @GetMapping("/query-list-2")
    public Result<PageInfo<DailyTrainQueryResp>> queryList2(@Valid DailyTrainQueryReq req) {
        return Result.success(dailyTrainService.queryList2(req));
    }

    @Operation(summary = "删除乘客")
    @DeleteMapping("/delete/{id}")
    public Result<Void> queryList(@PathVariable Long id) {
        dailyTrainService.del(id);
        return Result.success();
    }

    @Operation(summary = "生成余票")
    @GetMapping("/generate/{date}")
    public Result<Void> generateTicket(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable Date date) {
        dailyTrainService.generate(date);
        return Result.success();
    }

}

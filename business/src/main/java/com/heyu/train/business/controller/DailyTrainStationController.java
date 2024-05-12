package com.heyu.train.business.controller;

import com.heyu.train.business.req.DailyTrainStationQueryReq;
import com.heyu.train.business.req.DailyTrainStationSaveReq;
import com.heyu.train.business.resp.DailyTrainStationQueryResp;
import com.heyu.train.business.service.DailyTrainStationService;
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
@Tag(name = "乘客管理")
@Validated
@RequestMapping("/admin/daily-train-station")
@RequiredArgsConstructor
public class DailyTrainStationController {
    final DailyTrainStationService dailyTrainStationService;

    @Operation(summary= "新增/保存每日车站")
    @PostMapping("/save")
    public Result<Void> login(@Valid @RequestBody DailyTrainStationSaveReq req) {
        dailyTrainStationService.save(req);
        return Result.success();
    }
    @Operation(summary = "查询每日车站")
    @GetMapping("/query-list")
    public   Result<PageInfo<DailyTrainStationQueryResp>> queryList(@Valid DailyTrainStationQueryReq req) {
        return Result.success(dailyTrainStationService.queryList(req));

    }
    @Operation(summary = "删除每日车站")
    @DeleteMapping("/del/{id}")
    public   Result<Void> queryList(@PathVariable  Long id) {
        dailyTrainStationService.del(id);
        return  Result.success();

    }
}
